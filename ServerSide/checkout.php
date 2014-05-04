<?php

$lot_id = $_REQUEST["parking_lot_id"];

if(empty($lot_id)) {
    header("HTTP/1.0 400 Bad Request");
    die("Specify the lot from which you are checking out.");
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

if($current_count <= 0) {
    header("HTTP/1.0 400 Bad Request");
    die("The parking lot is already empty.");
}

$sql = "UPDATE parking_lot SET CurrentCount = CurrentCount - 1 where LotId = $lot_id";

$result = mysql_query($sql, $connection);

if(!$result) {
    header("HTTP/1.0 400 Bad Request");
    die("Could not update the parking lot.");
}

mysql_close($connection);

print "Successfully updated the current capacity.";

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

?>
