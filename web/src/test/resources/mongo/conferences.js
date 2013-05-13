db.conferences.drop();

db.conferences.insert(
	{
		conferenceId  : "CONF_1",
		conferenceUrl :	"dummy conference url",
		logoUrl		  :	"dummy logo url",
		title		  : "QCon New York 2013",
		summary		  : "QCon empowers software development by facilitating the spread of knowledge and innovation in the developer community. A practitioner-driven conference, QCon is designed for technical team leads, architects, engineering directors, and project managers who influence innovation in their teams.",
		startDate	  : new ISODate("2013-01-02T00:00:00Z"),
		endDate		  : new ISODate("2013-01-05T00:00:00Z"),
		country		  : "Japan",
		region		  : "Kanto",
		city		  : "Tokyo",
		address       : "dummy address",
		details		  : "QCon empowers software development by facilitating the spread of knowledge and innovation in the developer community. A practitioner-driven conference, QCon is designed for technical team leads, architects, engineering directors, and project managers who influence innovation in their teams. QCon highlights the most important development topics driving innovation - things you should be doing now or researching for your next project - presented by the doers in our community. Our conferences bring practitioners together with attendees who influence innovation in their teams: over half of conferences attendees, for example, have team lead or higher job titles. Additionally, QCons are staged in an intimate environment that promotes high-quality learning, peer-sharing, fun, and inspiration! QCon starts with 2 days of tutorials on Monday and Tuesday, June 10-11 followed by the full 3-day conference from Wednesday, June 12-14. The conference will feature over 100 speakers in 6 concurrent tracks daily covering the most timely and innovative topics driving the evolution of enterprise software development today. The setting is the beautiful, centrally-located Marriott at Brooklyn Bridge in New York City."
	}
);

db.conferences.insert(	
	{
		conferenceId  : "CONF_2",
		conferenceUrl :	"dummy conference url",
		logoUrl		  :	"dummy logo url",
		title		  : "QCon São Paulo: 2 dias, 50 palestras de alto nível técnico",
		summary		  : "O QCon SP traz, dias 4 e 5 de agosto, ícones internacionais e nacionais de diversas áreas, com apresentações aprofundadas de alto nível técnico.",
		startDate	  : new ISODate("2013-01-04T00:00:00Z"),
		endDate		  : new ISODate("2013-01-10T00:00:00Z"),
		country		  : "China",
		region		  : "North China",
		city		  : "Beijing",
		address       : "dummy address",
		details		  : "A Caelum e o InfoQ organizam, pela terceira vez no Brasil, o principal evento de arquitetos e desenvolvedores do mundo. O QCon SP traz, dias 4 e 5 de agosto, ícones internacionais e nacionais de diversas áreas, com apresentações aprofundadas de alto nível técnico. O QCon aborda não apenas uma única tecnologia ou aspecto: passa por Arquitetura, Linguagens Funcionais, Mobile, Agile, Práticas de Engenharia Ágil e outros. Dentre os keynotes desse ano teremos Tom Soderstrom, CTO do laboratório de propulsão da NASA, Martin Fowler, chief scientist da ThoughtWorks e Zach Holman, arquiteto do GitHub."
	}
);

db.conferences.insert(	
	{
		conferenceId  : "CONF_3",
		conferenceUrl :	"dummy conference url",
		logoUrl		  :	"dummy logo url",
		title		  : "QCon San Francisco 2013",
		summary		  : "Thank you for participating in this year's QCon San Francisco Conference. We hope that you enjoyed the conference and were able to go home with ideas, experience and new connections from the conference.",
		startDate	  : new ISODate("2013-01-14T00:00:00Z"),
		endDate		  : new ISODate("2013-01-20T00:00:00Z"),
		country		  : "Ukraine",
		city		  : "Kiev",
		address       : "dummy address",
		details		  : "Thank you for participating in this year's QCon San Francisco Conference. We hope that you enjoyed the conference and were able to go home with ideas, experience and new connections from the conference. QCon is a practitioner-driven conference designed for team leads, architects and project managers. The program includes two tutorial days led by over 80 industry experts and authors and three conference days with 18 tracks and over 80 speakers covering a wide variety of relevant and exciting topics in software development today. There is no other event in the US with similar opportunities for learning, networking, and tracking innovation occurring in the enterprise software development community."
	}
);

db.conferences.insert(	
	{
		conferenceId  : "CONF_4",
		conferenceUrl :	"dummy conference url",
		logoUrl		  :	"dummy logo url",
		title		  : "QCon London 2013. Training: March 4-5 // Conference: March 6-8",
		summary		  : "QCon London is the seventh annual London enterprise software development conference designed for developers, team leads, architects and project management is back!",
		startDate	  : new ISODate("2013-12-03T00:00:00Z"),
		endDate		  : new ISODate("2013-12-03T00:00:00Z"),
		country		  : "UK",
		city		  : "London",
		address       : "dummy address",
		details		  : "QCon London is the seventh annual London enterprise software development conference designed for developers, team leads, architects and project management is back! There is no other event in the UK with similar opportunities for learning, networking, and tracking innovation occurring in the Java, .NET, Html5, Mobile , Agile, and Architecture communities. Key takeaway points and the many blog discussions from last year's QCon London can be found in this article. Our concept has always been to present the latest developments as they become relevant and interesting for the software development community. With a 360 degree perspective we present new technology and trends in a non vendor forum to give the attendees inspiration, energy and desire to learn. Plus, we always have awesome speakers! QCon London is organized by Trifork A/S, a software development company situated in Aarhus, Denmark and InfoQ - Tracking change and innovation in the enterprise software development community."
	}
);

db.conferences.insert(	
	{
		conferenceId  : "CONF_5",
		conferenceUrl :	"dummy conference url",
		logoUrl		  :	"dummy logo url",
		title		  : "Architectural Hangover Cure",
		summary		  : "Some years ago your company adopted the 'framework | development tooling | cloud service' that was going to 'halve your development costs | quarter your bug turnaround time | delight your customers'.",
		startDate	  : new ISODate("2013-04-22T00:00:00Z"),
		endDate		  : new ISODate("2013-04-24T00:00:00Z"),
		country		  : "USA",
		region		  : "California",
		city		  : "Palo Alto",
		address       : "dummy address",
		details		  : "Some years ago your company adopted the 'framework | development tooling | cloud service' that was going to 'halve your development costs | quarter your bug turnaround time | delight your customers'. Sadly the technology you adopted did not deliver any of the benefits that the salesman promised - now it sits there like some obese octopus with its tentacles reaching into and choking your entire system. This track is all about strategies for escape, how to move on and never make that mistake again."
	}
);

db.conferences.insert(	
	{
		conferenceId  : "CONF_6",
		conferenceUrl :	"dummy conference url",
		logoUrl		  :	"dummy logo url",
		title		  : "Conference: 'The Java EE 7 Platform: Higher Productivity & Embracing HTML5'",
		summary		  : "The Java EE 7 platform has a changed scope and will now be focusing on Productivity and HTML5. JAX-RS 2 adds a new Client API to invoke the RESTful endpoints. JMS 2 is undergoing a complete overhaul to align with improvements in the Java language.",
		startDate	  : new ISODate("2013-04-22T00:00:00Z"),
		endDate		  : new ISODate("2013-04-23T00:00:00Z"),
		country		  : "UK",
		city		  : "London",
		address       : "dummy address",
		details		  : "The Java EE 7 platform has a changed scope and will now be focusing on Productivity and HTML5. JAX-RS 2 adds a new Client API to invoke the RESTful endpoints. JMS 2 is undergoing a complete overhaul to align with improvements in the Java language. Long awaited Batch Processing API and Caching API are also getting added to build applications using capabilities of the platform itself. Together these APIs will allow you to be more productive by simplifying enterprise development. WebSocket attempts to solve the issues and limitations of HTTP for real-time communication. A new API is getting added to build WebSocket driven applications. Processing JSON structures is inherent in any HTML5 applications and a new API to parse, generate, transform, and query JSON is being added to the platform. JavaServer Faces will add support for HTML5 forms. There are several other improvements coming in this latest version of the platform. The Java EE 7 platform is scheduled to release in Q2 2013. Some of the implementations are already integrated in GlassFish. This talk will provide a code-intensive introduction to the updated Java EE 7 platform. Several live demos will be shown during the talk. Don't miss out on this session to learn all about how to leverage the new and exciting standards in building your next enterprise application."
	}
);