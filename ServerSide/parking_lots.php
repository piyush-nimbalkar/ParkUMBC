<?php

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

$sql = "SELECT * from parking_lot";
$result = mysql_query($sql, $connection);
$count = mysql_numrows($result);

$parking_lot_json = array();

for ($i = 0; $i < $count; $i++) {
    $row = array(
        'parking_lot_id' => mysql_result($result, $i, "LotId"),
        'parking_lot_name' => mysql_result($result, $i, "Name"),
        'current_count' => mysql_result($result, $i, "CurrentCount"),
        'capacity' => mysql_result($result, $i, "Capacity")
    );
    array_push($parking_lot_json, $row);
}

mysql_close($connection);

$json_string = json_encode(array("parking_lots" => $parking_lot_json));
print $json_string;


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
