<?php

$lot_id = $_REQUEST["parking_lot_id"];
$lot_name = $_REQUEST["lot_name"];
$capacity = $_REQUEST["capacity"];
$current_count = 0;

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

    $sql = "CREATE TABLE parking_lot (
            LotId INT(11),
            PRIMARY KEY(LotId),
            Name VARCHAR(200),
            Capacity INT(5),
            CurrentCount INT(5)
    )";

    $result = mysql_query($sql, $connection);

    if(!$result) {
        header("HTTP/1.0 500 Internal Server Error");
        die('Invalid Query: ' . mysql_error());
    }
}

$sql = "INSERT INTO parking_lot (LotId, Name, Capacity, CurrentCount) VALUES ('$lot_id', '$lot_name', '$capacity', '$current_count')";

$result = mysql_query($sql, $connection);

if(!$result) {
    header("HTTP/1.0 400 Bad Request");
    die("Parking lot already exists.");
}

mysql_close($connection);

print("Successfully create the parking lot.");

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
