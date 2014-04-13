<?php
$conn_string = "host=fraude.openkvk.nl port=5432 dbname=opendata user=hackathon password=antifraude";
$con = pg_connect($conn_string);
if($con)
{
	$result = pg_query($conn_string, "select m.* from natuurlijkepersoon as n INNER JOIN maatschappelijkeactiviteit as m on n.subjectidpersoon = m.subjectidpersoon where burgerservicenummer = '".$_GET['bsn']."'");
}
while ($row = pg_fetch_row($result)) {
	var_dump($row);
}