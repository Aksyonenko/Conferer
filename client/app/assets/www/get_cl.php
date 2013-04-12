<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET");
//die('[{"conferenceID":"1", "title":"PHP conference 112", "date":"2013-04-14"},{"conferenceID":"2", "title":"Drupal conference", "date":"2013-04-16"}]');

$m = $_GET['month'];
$y = $_GET['year'];

die('[ {
  "id" : "51641da6e1fad4fb298dd382",
  "title" : "QCon New York 2013: ----- '.$y.'/'.$m.' -----",
  "summary" : "QCon empowers software development by facilitating the spread of knowledge and innovation in the developer community. A practitioner-driven conference, QCon is designed for technical team leads, architects, engineering directors, and project managers who influence innovation in their teams.",
  "startDate" : 1357084800000,
  "endDate" : 1357344000000,
  "country" : "Japan",
  "region" : "Kanto",
  "city" : "Tokyo",
  "details" : "QCon empowers software development by facilitating the spread of knowledge and innovation in the developer community. A practitioner-driven conference, QCon is designed for technical team leads, architects, engineering directors, and project managers who influence innovation in their teams. QCon highlights the most important development topics driving innovation - things you should be doing now or researching for your next project - presented by the doers in our community. Our conferences bring practitioners together with attendees who influence innovation in their teams: over half of conferences attendees, for example, have team lead or higher job titles. Additionally, QCons are staged in an intimate environment that promotes high-quality learning, peer-sharing, fun, and inspiration! QCon starts with 2 days of tutorials on Monday and Tuesday, June 10-11 followed by the full 3-day conference from Wednesday, June 12-14. The conference will feature over 100 speakers in 6 concurrent tracks daily covering the most timely and innovative topics driving the evolution of enterprise software development today. The setting is the beautiful, centrally-located Marriott at Brooklyn Bridge in New York City.",
  "sessions" : null,
  "logoURL" : "/img/logo.png"
}, {
  "id" : "51641daee1fad4fb298dd383",
  "title" : "QCon São Paulo: 2 dias, 50 palestras de alto nível técnico",
  "summary" : "O QCon SP traz, dias 4 e 5 de agosto, ícones internacionais e nacionais de diversas áreas, com apresentações aprofundadas de alto nível técnico.",
  "startDate" : 1357257600000,
  "endDate" : 1357776000000,
  "country" : "China",
  "region" : "North China",
  "city" : "Beijing",
  "details" : "A Caelum e o InfoQ organizam, pela terceira vez no Brasil, o principal evento de arquitetos e desenvolvedores do mundo. O QCon SP traz, dias 4 e 5 de agosto, ícones internacionais e nacionais de diversas áreas, com apresentações aprofundadas de alto nível técnico. O QCon aborda não apenas uma única tecnologia ou aspecto: passa por Arquitetura, Linguagens Funcionais, Mobile, Agile, Práticas de Engenharia Ágil e outros. Dentre os keynotes desse ano teremos Tom Soderstrom, CTO do laboratório de propulsão da NASA, Martin Fowler, chief scientist da ThoughtWorks e Zach Holman, arquiteto do GitHub.",
  "sessions" : null,
  "logoURL" : "/img/logo.png"
}, {
  "id" : "51641e58e1fad4fb298dd384",
  "title" : "QCon San Francisco 2013",
  "summary" : "Thank you for participating in this year\'s QCon San Francisco Conference. We hope that you enjoyed the conference and were able to go home with ideas, experience and new connections from the conference.",
  "startDate" : 1358121600000,
  "endDate" : 1358640000000,
  "country" : "Ukraine",
  "region" : null,
  "city" : "Kiev",
  "details" : "Thank you for participating in this year\'s QCon San Francisco Conference. We hope that you enjoyed the conference and were able to go home with ideas, experience and new connections from the conference.",
  "sessions" : null,
  "logoURL" : "/img/logo.png"
} ]');