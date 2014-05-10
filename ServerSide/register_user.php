<?php

$registration_id = $_REQUEST["registration_id"];

if(empty($registration_id)) {
    header("HTTP/1.0 400 Bad Request");
    die("Registration Id cannot be blank.");
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

if(!table_exists("user")) {

    $sql = "CREATE TABLE user (RegistrationId VARCHAR(255))";

    $result = mysql_query($sql, $connection);

    if(!$result) {
        header("HTTP/1.0 500 Internal Server Error");
        die('Invalid Query: ' . mysql_error());
    }
}

$sql = "INSERT INTO user (RegistrationId) VALUES ('$registration_id')";

mysql_query($sql, $connection);

mysql_close($connection);

print("Device successfully registered");

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
