<?php

$lot_id = $_REQUEST["parking_lot_id"];

if(empty($lot_id)) {
    header("HTTP/1.0 400 Bad Request");
    die("Specify the lot in which you are parking.");
}

$db_username = file_get_contents('./database_config.txt', NULL, NULL, 0, 11);
$db_password = file_get_contents('./database_config.txt', NULL, NULL, 0, 15);
$connection = mysql_connect("localhost", $db_username, $db_password);

if(!$connection) {
    header("HTTP/1.0 500 Internal Server Error");
    die('Could not connect: ' . mysql_error());
}

$db_selected = mysql_select_db("park_umbc", $connection);

if(!$db_selected) {
    header("HTTP/1.0 500 Internal Server Error");
    die('Can\'t use park_umbc: ' . mysql_error());
}

if(!table_exists("parking_lot")) {
    header("HTTP/1.0 500 Internal Server Error");
    die('Oops we are not in production currently!');
}

$sql = "SELECT * from parking_lot where LotId = $lot_id";

$result = mysql_query($sql, $connection);

if(!$result or mysql_numrows($result) == 0) {
    header("HTTP/1.0 400 Bad Request");
    die("Invalid parking lot.");
}

$capacity = mysql_result($result, 0, "Capacity");
$current_count = mysql_result($result, 0, "CurrentCount");

if($capacity <= $current_count) {
    header("HTTP/1.0 400 Bad Request");
    die("The parking lot is already full.");
}

$sql = "UPDATE parking_lot SET CurrentCount = CurrentCount + 1 where LotId = $lot_id";

$result = mysql_query($sql, $connection);

if(!$result) {
    header("HTTP/1.0 400 Bad Request");
    die("Could not update the parking lot.");
}

$reg_ids = array();

$sql = "SELECT DISTINCT RegistrationId FROM user";
$result = mysql_query($sql, $connection);
$count = mysql_numrows($result);

for ($i = 0; $i < $count; $i++) {
    $reg_ids[] = mysql_result($result, $i, "RegistrationId");
}

$sql = "SELECT Name FROM parking_lot where LotId = $lot_id";
$result = mysql_query($sql, $connection);
$lot_name = mysql_result($result, 0, "Name");

$message = array("parking_lot_id" => $lot_id, "parking_lot_name" => $lot_name);
send_notification($reg_ids, $message);

mysql_close($connection);

print "Your parking request has been successfully registered.";


function table_exists($tablename, $database = false) {
    if(!$database) {
        $res = mysql_query("SELECT DATABASE()");
        $database = mysql_result($res, 0);
    }

    $res = mysql_query("
        SELECT COUNT(*) AS count
        FROM information_schema.tables
        WHERE table_schema = '$database'
        AND table_name = '$tablename'
    ");

    return mysql_result($res, 0) == 1;
}

function send_notification($reg_ids, $message) {
    $url = 'https://android.googleapis.com/gcm/send';

    $fields = array(
        'registration_ids' => $reg_ids,
        'data' => $message,
    );

    $headers = array(
        'Authorization: key=AIzaSyAC1YpmwknTqSmf6YDS9VlviRV3W8NsYOA',
        'Content-Type: application/json'
    );

    $ch = curl_init();

    curl_setopt($ch, CURLOPT_URL, $url);

    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));

    $result = curl_exec($ch);
    if ($result === FALSE) {
        die('Curl failed: ' . curl_error($ch));
    }

    curl_close($ch);
}

?>
