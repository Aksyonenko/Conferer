DELETE FROM conferences;
INSERT INTO conferences (id, conferenceUrl, logoUrl, title, summary, startDate, endDate, country, region, city, address, details) VALUES (
	1,
	'dummy conference url',
	'dummy logo url',
	'QCon New York 2013',
	'QCon empowers software development by facilitating the spread of knowledge and innovation in the developer community. A practitioner-driven conference, QCon is designed for technical team leads, architects, engineering directors, and project managers who influence innovation in their teams.',
	PARSEDATETIME('02-01-2013T00:00:00.000+0900', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('05-01-2013T00:00:00.000+0900', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Japan',
	'Kanto',
	'Tokyo',
	'dummy address',
	'QCon empowers software development by facilitating the spread of knowledge and innovation in the developer community. A practitioner-driven conference, QCon is designed for technical team leads, architects, engineering directors, and project managers who influence innovation in their teams. QCon highlights the most important development topics driving innovation - things you should be doing now or researching for your next project - presented by the doers in our community. Our conferences bring practitioners together with attendees who influence innovation in their teams: over half of conferences attendees, for example, have team lead or higher job titles. Additionally, QCons are staged in an intimate environment that promotes high-quality learning, peer-sharing, fun, and inspiration! QCon starts with 2 days of tutorials on Monday and Tuesday, June 10-11 followed by the full 3-day conference from Wednesday, June 12-14. The conference will feature over 100 speakers in 6 concurrent tracks daily covering the most timely and innovative topics driving the evolution of enterprise software development today. The setting is the beautiful, centrally-located Marriott at Brooklyn Bridge in New York City.'
), (
	2,
	'dummy conference url',
	'dummy logo url',
	'QCon São Paulo: 2 dias, 50 palestras de alto nível técnico',
	'O QCon SP traz, dias 4 e 5 de agosto, ícones internacionais e nacionais de diversas áreas, com apresentações aprofundadas de alto nível técnico.',
	PARSEDATETIME('04-01-2013T00:00:00.000+0800', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('10-01-2013T00:00:00.000+0800', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),	
	'China',
	'North China',
	'Beijing',
	'dummy address',
	'A Caelum e o InfoQ organizam, pela terceira vez no Brasil, o principal evento de arquitetos e desenvolvedores do mundo. O QCon SP traz, dias 4 e 5 de agosto, ícones internacionais e nacionais de diversas áreas, com apresentações aprofundadas de alto nível técnico. O QCon aborda não apenas uma única tecnologia ou aspecto: passa por Arquitetura, Linguagens Funcionais, Mobile, Agile, Práticas de Engenharia Ágil e outros. Dentre os keynotes desse ano teremos Tom Soderstrom, CTO do laboratório de propulsão da NASA, Martin Fowler, chief scientist da ThoughtWorks e Zach Holman, arquiteto do GitHub.'
), (
	3,
	'dummy conference url',
	'dummy logo url',
	'QCon San Francisco 2013',
	'Thank you for participating in this year''s QCon San Francisco Conference. We hope that you enjoyed the conference and were able to go home with ideas, experience and new connections from the conference.',
	PARSEDATETIME('14-01-2013T00:00:00.000+0200', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('20-01-2013T00:00:00.000+0200', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),	
	'Ukraine',
	NULL,
	'Kiev',
	'dummy address',
	'Thank you for participating in this year''s QCon San Francisco Conference. We hope that you enjoyed the conference and were able to go home with ideas, experience and new connections from the conference. QCon is a practitioner-driven conference designed for team leads, architects and project managers. The program includes two tutorial days led by over 80 industry experts and authors and three conference days with 18 tracks and over 80 speakers covering a wide variety of relevant and exciting topics in software development today. There is no other event in the US with similar opportunities for learning, networking, and tracking innovation occurring in the enterprise software development community.'
), (
	4,
	'dummy conference url',
	'dummy logo url',
	'QCon London 2013. Training: March 4-5 // Conference: March 6-8',
	'QCon London is the seventh annual London enterprise software development conference designed for developers, team leads, architects and project management is back!',
	PARSEDATETIME('12-03-2013T00:00:00.000+0000', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('12-03-2013T00:00:00.000+0000', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'UK',
	NULL,
	'London',
	'dummy address',
	'QCon London is the seventh annual London enterprise software development conference designed for developers, team leads, architects and project management is back! There is no other event in the UK with similar opportunities for learning, networking, and tracking innovation occurring in the Java, .NET, Html5, Mobile , Agile, and Architecture communities. Key takeaway points and the many blog discussions from last year''s QCon London can be found in this article. Our concept has always been to present the latest developments as they become relevant and interesting for the software development community. With a 360 degree perspective we present new technology and trends in a non vendor forum to give the attendees inspiration, energy and desire to learn. Plus, we always have awesome speakers! QCon London is organized by Trifork A/S, a software development company situated in Aarhus, Denmark and InfoQ - Tracking change and innovation in the enterprise software development community.'
), (
	5,
	'dummy conference url',
	'dummy logo url',
	'Architectural Hangover Cure',
	'Some years ago your company adopted the "framework | development tooling | cloud service" that was going to "halve your development costs | quarter your bug turnaround time | delight your customers".',
	PARSEDATETIME('22-04-2013T00:00:00.000-0700', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('24-04-2013T00:00:00.000-0700', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'USA',
	'California',
	'Palo Alto',
	'dummy address',
	'Some years ago your company adopted the "framework | development tooling | cloud service" that was going to "halve your development costs | quarter your bug turnaround time | delight your customers". Sadly the technology you adopted did not deliver any of the benefits that the salesman promised - now it sits there like some obese octopus with its tentacles reaching into and choking your entire system. This track is all about strategies for escape, how to move on and never make that mistake again.'
), (
	6,
	'dummy conference url',
	'dummy logo url',
	'The Java EE 7 Platform: Higher Productivity & Embracing HTML5',
	'The Java EE 7 platform has a changed scope and will now be focusing on Productivity and HTML5. JAX-RS 2 adds a new Client API to invoke the RESTful endpoints. JMS 2 is undergoing a complete overhaul to align with improvements in the Java language.',
	PARSEDATETIME('22-04-2013T00:00:00.000+0000', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('23-04-2013T00:00:00.000+0000', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'UK',
	NULL,
	'London',
	'dummy address',
	'The Java EE 7 platform has a changed scope and will now be focusing on Productivity and HTML5. JAX-RS 2 adds a new Client API to invoke the RESTful endpoints. JMS 2 is undergoing a complete overhaul to align with improvements in the Java language. Long awaited Batch Processing API and Caching API are also getting added to build applications using capabilities of the platform itself. Together these APIs will allow you to be more productive by simplifying enterprise development. WebSocket attempts to solve the issues and limitations of HTTP for real-time communication. A new API is getting added to build WebSocket driven applications. Processing JSON structures is inherent in any HTML5 applications and a new API to parse, generate, transform, and query JSON is being added to the platform. JavaServer Faces will add support for HTML5 forms. There are several other improvements coming in this latest version of the platform. The Java EE 7 platform is scheduled to release in Q2 2013. Some of the implementations are already integrated in GlassFish. This talk will provide a code-intensive introduction to the updated Java EE 7 platform. Several live demos will be shown during the talk. Don''t miss out on this session to learn all about how to leverage the new and exciting standards in building your next enterprise application.'
);

DELETE FROM sessions;
INSERT INTO sessions (id, title, summary, startTime, endTime, type) VALUES (
	1,
	'Avoiding Invisible Impediments to High Performance',
	'This tutorial assumes the following hypothesis: Learning is the Bottleneck of Software Development and Delivery, and asks the question "what is keeping us from learning effectively?" There are some things that are visible such as the length of the release cycle, the clarity of the goal, learning from mistakes and difficulties, etc.... This is valuable, but not what this tutorial is about.',
	PARSEDATETIME('02-01-2013T09:00:00.000+0900', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('02-01-2013T11:00:00.000+0900', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Workshop'
), (
	2,
	'Continuous Delivery',
	'This tutorial will be delivered as an in-depth, interactive talk. It will describe the ideas of Continuous Delivery as a practical everyday process, highlighting some of the techniques developed while working on a complex real world CD project. It will provide an overview of the key process details: describing deployment pipelining as a technique to structure automation, pipeline monitoring to gain insight into progress and status, approaches to configuration management, automated acceptance testing and automated deployment as well as many other aspects of the CD approach to software delivery.',
	PARSEDATETIME('03-01-2013T09:00:00.000+0900', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('03-01-2013T11:00:00.000+0900', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Presentation'
), (
	3,
	'Creating iOS Apps in Objective-C',
	'This is a one-day tutorial in writing iOS apps with the application Xcode. An iPhone or iPad app is made of objects that communicate with each other. Learn to create objects and send them messages in the language Objective-C. Use them to draw text and graphics on the screen, detect and respond to a touch, perform simple animations, display controls such as buttons and sliders, and play background music. Special topics include "collections"',
	PARSEDATETIME('04-01-2013T09:00:00.000+0900', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('04-01-2013T11:00:00.000+0900', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),	
	'Tutorial'
), (
	4,
	'Building Web Applications with WebSockets',
	'WebSockets brings new capabilities to the web ? reliable, low-latency, low-overhead web communications ? but also requires new skills and ways of looking at development. Developers and security professionals have been struggling to fit this new technology into their existing models of the web. It''s time to look at the fundamentals of WebSocket communication and web development, then use these to build a new way of understanding web development. In this session, we''ll look deeply at the WebSocket protocol, see how to apply it to common design patterns (broadcast, request-response, and mediated peer-to-peer), then apply this knowledge to building HTML5 WebSocket clients. We''ll also look at how to make this work on the desktop and mobile devices. We''ll also cover the testing issues that arise when developing on multiple platforms and provide real-world strategies for efficient testing. You''ll take home pointers to resources, roadmaps for learning the client-side technologies, and a copy of the finished application source code to jump-start your own development efforts.',
	PARSEDATETIME('04-01-2013T09:00:00.000+0900', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('04-01-2013T11:00:00.000+0900', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Workshop'
), (
	5,
	'Testable JavaScript',
	'As with most things, getting started is the hardest part. This is especially true of testing in general and an unfortunately reality with JavaScript testing in particular. Beyond the myriad of choices available to the JavaScript community for testing there is the persistent problem of running the tests automatically in a browser (or several browsers) during the normal build and release process.This tutorial will finally give you the chance to get started! Bring your own JavaScript and we will start the test process together - from writing unit tests, to integration tests, all the way to running them manually and most importantly run them automatically as part of an automated process. Along the way we will investigate ideas for refactoring your code to make it more testable and give quick overviews about testable patterns and how they apply to your JavaScript.We will use the Jasmine as the unit test framework, Karma (nee Testacular) for automation, and Istanbul for code coverage as we construct a complete JavaScript testing solution. We will also touch upon WebDriver and how it fits into client-side JavaScript integration testing. By the end of the tutorial you will have a solid foundation upon which to build out a complete suite of automated tests - along with canonical examples of written tests to use as a template for writing the remainder of your tests.',
	PARSEDATETIME('2013-01-04T09:00:00.000+0800', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('2013-01-04T11:00:00.000+0800', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Workshop'
), (
	6,
	'Presentation Patterns for Better Technical Talks',
	'(big objects that contain smallers ones) and "view controllers" (objects that manage a series of views).Hardware and software: Macs with the application Xcode.',
	PARSEDATETIME('2013-01-04T10:30:00.000+0800', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('2013-01-04T12:00:00.000+0800', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Presentation'
), (
	7,
	'Designing and Implementing Hypermedia APIs',
	'Hypermedia APIs are getting some buzz. But what are they, really? What is the difference between common URI-based CRUD API designs and hypermedia-style APIs? How do you implement a hypermedia API and when does it make sense to use a hypermedia design instead of a CRUD-based approach? Based on the Mike Amundsen''s multi-part InfoQ article series of the same name, this fast paced, hands-on four-hour workshop shows attendees how to design a hypermedia style API, how to implement a server that supports varying hypermedia responses, and how to build clients that can take advantage of hypermedia. Additional time will be spent exploring when clients break and how reliance on hypermedia can reduce the need for re-coding and re-deploying client applications while still supporting new features in the API. Hands-on labs include authoring a hypermedia format for your API, designing server-side components that can emit hypermedia responses, and deciding on which client-side style of hypermedia fits best for your needs. A final challenge will be to update the server-side responses with new features that do not break existing hypermedia clients.',
	PARSEDATETIME('2013-01-04T13:00:00.000+0800', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('2013-01-04T14:00:00.000+0800', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Tutorial'
), (
	8,
	'RESTful Web APIs',
	'Based on the upcoming O''Reilly book "RESTful Web APIs" by Leonard Richardson and Mike Amundsen, this 1/2 day workshop covers the basics of Fielding''s REST style, HTTP standards, and common practices for APIs for Web. Key topics such as how how use hypermedia to increase API flexibility and how application profiles can improve API interoperability are also covered. In addition, a wide range of existing message formats and semantic vocabularies are reviewed along with a procedure for selecting and applying these existing standards to your own implementations. Other subjects will be covered such as caching, versioning, and supporting RESTful APIs on protocols other an HTTP.Throughout the workshop, attendees will be able to apply step-by-step guidance on how to create their own RESTful Web API and share these designs with the group at the end of the session.',
	PARSEDATETIME('2013-01-14T09:00:00.000+0200', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('2013-01-14T11:00:00.000+0200', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Tutorial'
), (
	9,
	'Get Good with Git and GitHub',
	'You''ve heard your friends say that "Git is the version control system of the future." You may have also heard such hallway talk as "A GitHub account is the the new resume." But there are a lot of technologies to chase these days and you just might not have had time for Git until now. This half-day workshop takes you from never having used Git all the way up to daily-workflow proficiency with both Git and GitHub. We''ll help demystify the directed acyclic graph manipulations that Git performs, not by oversimplifying, but rather by dissecting these complex data structure operations. Our unique disassembly of Git into layers helps computer science-domain practitioners deeply understand "how Git works" and enables them to leverage this newfound knowledge back at the office for version control, collaborative development, and release management excellence.',
	PARSEDATETIME('2013-01-14T09:00:00.000+0200', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('2013-01-14T11:00:00.000+0200', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Presentation'
), (
	10,
	'Responsive Web Design',
	'Useful and effective techniques on building web pages that adjust to the user.',
	PARSEDATETIME('2013-03-12T10:00:00.000+0000', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('2013-03-12T12:00:00.000+0000', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Tutorial'
), (
	11,
	'Creating Android Apps in Java',
	'This is a one-day tutorial in writing Android apps with the application Eclipse. An app is made of two parts: a screen layout written in XML (Extensible Markup Language), and objects written in Java. Learn how the two halves of the app communicate with each other. Draw text and graphics on the screen, detect and respond to a touch, perform simple animations, display controls such as buttons and sliders, and play background music. Two examples of Android design patterns: plugging a "listener" into a "view" to make it touch-sensitive; and plugging an "adapter" into an "adapter view" to let it read items from a source of data.Hardware and software: Macs or PCs with the JDK (Java Development Kit), the application Eclipse (currently version 4.2 [Juno]), Android SDK and ADT.',
	PARSEDATETIME('2013-03-12T14:00:00.000+0000', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('2013-03-12T16:00:00.000+0000', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Presentation'
), (
	12,
	'Building Windows Store Apps with XAML/C# for Windows 8/RT',
	'Microsoft has made bold moves on the software and hardware front for consumers with the launch of Windows 8 and Windows RT, introducing the new WinRT application development platform for building Windows Store Apps. WinRT brings a new style of touch-first user experience, a sand-boxed application runtime with contract-based inter-app communication, a mobile-optimized execution lifecycle and rich notification support. In this 6-hour event, you''ll experience the details of these new platform features through a combination of in-depth explanation, demonstration, code samples and end-to-end application building. Agenda: Building your first Windows Store app including platform/tools tour, basic app, minimum feature requirements for certifications.',
	PARSEDATETIME('2013-03-12T10:00:00.000-0700', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('2013-03-12T12:00:00.000-0700', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Tutorial'
), (
	13,
	'Hot Technology for Financial Services',
	'Senior architects and alpha developers in the Finance industry are constantly looking for cutting edge technology to gain competitive advantage. This community delivers intense applications that require hyper-fast computation, reliable/secure connectivity, high-performance messaging and extremely-scalable big-data analytics. This track will explore innovative techniques to manage large data-sets in Risk and Trading systems, near-time delivery with mobile web applications and intranet, novel approaches to high-performance computation (HPC) and finally allowing the machines to extract intelligence out of it all. Premier speakers will discuss their approaches and their applicability to capital and money markets, retail/investment banking, trading systems, credit card companies, securities exchanges, etc.',
	PARSEDATETIME('2013-04-22T11:00:00.000-0700', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('2013-04-22T12:00:00.000-0700', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Open Discussion'
), (
	14,
	'Applied Data Science',
	'Big data big data, what you gonna do? what you gonna when the data scientist comes for you? This track covers the tools required to be a data scientist and ways in which the data scientist can empower the rest of the organization. Come learn how to traverse complex graphs, hear how NASA is extracting value from data, and particpate in hands-on demonstrations of dealing with large scale datasets',
	PARSEDATETIME('2013-04-22T14:00:00.000-0700', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('2013-04-22T16:00:00.000-0700', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Open Discussion'
), (
	15,
	'Architectures You''ve Always Wondered about',
	'Have you ever browsed to a site like Facebook or Amazon and wondered what sort of software architecture they may have used, or what insights their teams must have after solving such complex and large-scale problems? This track will give you the opportunity to learn directly from some of the most well-known and high-volume web applications in the world. In previous QCon conferences, this track has featured presentations by Twitter, eBay, Amazon, Facebook, LinkedIn, and Netflix.',
	PARSEDATETIME('2013-04-23T10:00:00.000-0700', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('2013-04-23T12:00:00.000-0700', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Workshop'
), (
	16,
	'Polyglot Architectures',
	'There is an ever growing trend for organisations to be mixing and matching several programming languages and platforms in their architectures. This allows teams to use best of breed tools and even heterogenuous programming paradigms, in order to deliver a product faster and in a higher quality. But multi-platform architectures also pose many issues for architects, managers, team leaders etc. This track explores the benefits associated with persuing a polyglot arcitecture - and what the hidden price for doing polyglot programming.',
	PARSEDATETIME('2013-04-23T14:00:00.000-0700', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('2013-04-23T16:00:00.000-0700', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Workshop'
), (
	17,
	'HTML5 and Modern Web Languages',
	'There''s no disagreement that HTML5 is fostering a new web endowed with a cornucopia of functionality and power. But as developers, how do we create and harness all these features for this new web? What types of new programming languages and development environments beyond vanilla JavaScript are available to help us build truly sophisticated and innovative applications. And most importantly, we need new tools, techniques and mechanisms that help us build more maintainable and manageable systems.',
	PARSEDATETIME('2013-04-23T10:00:00.000-0700', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('2013-04-23T12:00:00.000-0700', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Open Discussion'
), (
	18,
	'The JavaScript Ecosystem',
	'In 2007 Steve Yegge in his famous &qt;The Next Big Language&qt; blog post, was speculating about how big JavaScript would become - and he was right! With innovations like V8, Node.js, CoffeeScript, client-side MVC frameworks, and more, it has become the fastest growing programming platform ever! In this tracks leading experts in the field will provide valuable insight into how they leverage JavaScript for building innovative products and services. Presentations will cover several issues like popular libraries and tools, motivations, best-practices, deployment patterns and more.',
	PARSEDATETIME('2013-04-22T15:00:00.000+0000', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('2013-04-22T16:00:00.000+0000', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Presentation'
), (
	19,
	'Lean Startup Applied',
	'The Lean Startup movement isn''t just for small companies. No company can afford to continue to undertake large software projects that don''t end up providing a meaningful ROI. In this track we look at how your organization can become more efficient at delivering business value by better adopting the processes and practices that successful lean startups apply.',
	PARSEDATETIME('2013-04-23T13:00:00.000+0000', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	PARSEDATETIME('2013-04-23T16:00:00.000+0000', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ'),
	'Presentation'
);

DELETE FROM speakers;
INSERT INTO speakers (id, speakerUrl, firstName, lastName, photoUrl, about, facebook, twitter, linkedin) VALUES (
	1,
	'some url',
	'Adam',
	'Blum',
	'some photo url',
	'Toby O''Rourke is a Technical Architect at Gamesys, a London based soft-gaming company. With a background as a Java guy, he considers himself lucky to rove around the tech landscape at Gamesys combining architecture and hands on development with exposure to a fairly eclectic mix of client, server and native mobile technologies. He believes an excited team, plenty of domain modelling and a beady eye on the details are decent starting point for delivering just about anything.',
	'facebook link',
	'twitter link',
	'linkedin link',
), (
	2,
	'some url',
	'Alain',
	'Hélaïli',
	'some photo url',
	'Tom Coupland is a Senior Engineer at Nokia Entertainment Bristol. Having worked with transaction processing monoliths, producing one of his own, he''s now enjoying the world of SOA and the freedoms it can bring. After years of Java, he set out to find more productive and elegant tools, currently very much enjoying spending time with Clojure. When not trying to make his bit of the world of software a better place, he can be found out in the real one, running, preferably in the mud, wind and rain. Twitter: @tcoupland',
	'facebook link',
	'@tcoupland',
	'linkedin link',
), (
	3,
	'some url',
	'Andrew',
	'Elmore',
	'some photo url',
	'Torben Hoffmann is Product & Research Manager for Erlang Solutions. He has been active in the Erlang community for several years and has spoken at conferences world-wide. His first big Erlang related project was the introduction of Erlang as a technology to write a gateway in for Motorola Solutions in Denmark. Torben studied Engineering at the Technical University of Denmark. Twitter: @LeHoff',
	'facebook link',
	'twitter link',
	'linkedin link',
), (
	4,
	'some url',
	'Bryan',
	'Dove',
	'some photo url',
	'Remko van Dokkum is an interaction design engineer and works together with Ruud Lanfermeijer since the day he graduated in 1998. His main interest is ''enabling technology'', creating and engineering interactive environments were you can play with and discover all tomorrow''s technology has to offer. By the motto "Sky is the Limit" hardware and software are created from scratch to fit the needs of projects. A long list of technology is being incorporated in projects over the past years, to mention a few: motion tracking, biometric sensors, human DNA translation, interactive 3D model, projection mapping, Arduino, and VJ interface for Hammond organ. Twitter: @remkovandokkum',
	'facebook link',
	'twitter link',
	'linkedin link',
), (
	5,
	'some url',
	'Christophe',
	'Coenraets',
	'some photo url',
	'Rob started his career in the Army leaving in 2007, moving from there to a small consultancy delivering solutions to support, among others the UK MOD, at an operational and strategic level. Recently as the CTO of Sphonic he has been focused on delivering a market leading solution focus on anti fraud market, supporting merchants manage the risk of Cardholder / Card not present transactions, delivering high volume low latency tx using a raft of new technologies. He has keen interest in finding the right technology more recently this is focused on Cassandra, Scala and Mongo with a bit of Rails and Postgresql.',
	'facebook link',
	'twitter link',
	'linkedin link',
), (
	6,
	'some url',
	'Dio',
	'Synodinos',
	'some photo url',
	'Robbie has been making phones ring, building clouds and making websites since 2005. He has been fortunate enough to have been practicing agile that whole time and moved to New York from London to join consultancy Pivotal Labs in early 2012 working with Rails and Javascript. Twitter: @robb1e',
	'facebook link',
	'twitter link',
	'linkedin link',
), (
	7,
	'some url',
	'Jamie',
	'Engesser',
	'some photo url',
	'Robert Annett has been a developer since 1995 and worked in industries from energy management to investment banking. Much of this work has involved upgrading and migrating legacy systems with the occasional green-field project (with integration to legacy systems, of course). He has worked in the IT industry long enough to realize that ALL successful systems become legacy eventually. Twitter: @robert_annett',
	'facebook link',
	'twitter link',
	'linkedin link',
), (
	8,
	'some url',
	'Israel',
	'Boza Rodriguez',
	'some photo url',
	'Rob is a director of the FT''s Labs division, which works on experimental web technologies and produces products such as the FT web app.  He is currently responsible for the technical delivery of the FT web app and its hosting infrastructure.  Prior to FT Labs, Rob founded the web consulting firm Assanka, which spent 8 years working on innovative web projects for clients including News International, The Economist Group and the FT, before being acquired by the FT in January 2012. Twitter: @rtshilston',
	'facebook link',
	'twitter link',
	'linkedin link',
), (
	9,
	'some url',
	'Richard',
	'Croucher',
	'some photo url',
	'Romilly Cocking is a member of the Highgate Guild of Software practitioners. He consults best practices for Agile Development, team dynamics, domain specific languages, code generation, tools to aid thinking and decision making (Mind Maps, Topic Maps etc.), metacognition (Thinking about Thinking) and computer-assisted learning. Twitter: @romillyc',
	'facebook link',
	'twitter link',
	'linkedin link',
), (
	10,
	'some url',
	'Robert',
	'Annett',
	'some photo url',
	'Rickard has worked on several OpenSource projects that involve J2EE development, such as JBoss, XDoclet and WebWork. He has also been the principal architect of the SiteVision CMS/portal platform, where he used AOP as the foundation. Now he works for Neo Technology, building the leading graph database Neo4j. Twitter: @rickardoberg',
	'facebook link',
	'twitter link',
	'linkedin link',
), (
	11,
	'some url',
	'Steve',
	'Freeman',
	'some photo url',
	'Richard Garsthagen is the Director of Cloud Business Development for Oracle. He is responsible for positioning Oracle''s Cloud Computing products and Public Cloud services throughout Europe. With over 15 years experience working for high tech companies including Citrix and VMware, Richard has gained an extensive knowledge of Virtualization and Cloud Computing Solutions available today. His deep technical knowledge and enthusiasm for Cloud Computing enables him to clearly promote the Oracle Cloud Solutions to a broad audience daily. Twitter: @the_anykey',
	'facebook link',
	'twitter link',
	'linkedin link',
), (
	12,
	'some url',
	'Ward',
	'Cunningham',
	'some photo url',
	'Richard has many year experience of Banking IT systems in both consulting and permanent roles with Barclays, Commerzbank, CreditSuisse, DeutscheBank, HSBC, Flowtraders, Merrill Lynch and RBS. He is experienced at of all layers of the infrastructure and with devices from Smartphones to Mainframes. He programs in multiple languages, having started with Assembler on his self built computers. He transitioned through Pascal, C, C++, Java and C# and is now a strong proponent of Erlang. He was also Chief Architect for one of the first Cloud based Grid offerings designed to support Banks, was lead architect in the DevOps Cloud team at Microsoft and has has helped several Cloud startups. Richard enjoys learning new technologies and finding new ways to use existing ones.',
	'facebook link',
	'twitter link',
	'linkedin link',
), (
	13,
	'some url',
	'Trisha',
	'Gee',
	'some photo url',
	'Remko van Dokkum is an interaction design engineer and works together with Ruud Lanfermeijer since the day he graduated in 1998. His main interest is ''enabling technology'', creating and engineering interactive environments were you can play with and discover all tomorrow''s technology has to offer. By the motto "Sky is the Limit" hardware and software are created from scratch to fit the needs of projects. A long list of technology is being incorporated in projects over the past years, to mention a few: motion tracking, biometric sensors, human DNA translation, interactive 3D model, projection mapping, Arduino, and VJ interface for Hammond organ. Twitter: @remkovandokkum',
	'facebook link',
	'twitter link',
	'linkedin link',
), (
	14,
	'some url',
	'Zoe',
	'Slattery',
	'some photo url',
	'Rain Ashford is in her 2nd year of PhD research on the Art and Computational Technology program in the Department of Computing at Goldsmiths, University of London. Her work involves investigating sensing technologies and how these can be applied to the body in the form of garments and wearable artworks to measure and visualise mood, health and lifestyle data. She''s interested in how our bodies unconsciously react to social situations in particular and the physiological signals we can track and visualise via data. Rain also creates fun, interactive and aesthetically pleasing artworks and wearables that include gaming and musical elements. She''s keen to show that coding, electronics, components and circuitry doesn''t have to be cold, boring, hard and sharp, that can be fun, colourful and elegant, and also can be part of an overall design that is interesting to the user. Rain uses a variety of materials and electronic components such as sensors to measure EEG (Electroencephalography), GSR (Galvanic Skin Response) heart rate, temperature, proximity, magnetic fields and movement, and C programming language. Rain is a freelance consultant for online learning projects, she also devises and teaches workshops in coding and electronics via e-textiles and wearable technology. Her background is in developing online learning activities for the BBC as Senior Producer at BBC Learning and Technologist at BBC R&D, where she co-ran BBC Backstage and Women in Technology network.',
	'facebook link',
	'twitter link',
	'linkedin link',
), (
	15,
	'some url',
	'Alex',
	'Batlin',
	'some photo url',
	'Raf is based in Cornwall, from where he runs Dreamthought Technologies Ltd, an Agile biased software consulting firm. He has worked as a software engineer for over thirteen years, developing enterprise solutions in the industries of investment banking, e-commerce, data-services, telecommunications and media. His client projects have included performance optimisation for the BBC''s iPlayer and development of market reference data systems within ING and Morgan Stanley. He is currently on contract at Headforwards Ltd in Cornwall, developing cloud virtualisation software in a Kanban team by the sea. Raf is a proponent for lean practices, TDD and home-schooling. He has soft spots for Modern Perl, Scala and node.js. Twitter: @fiqus',
	'facebook link',
	'twitter link',
	'linkedin link',
);

INSERT INTO conferences_sessions (CONFERENCE_ID, SESSION_ID) VALUES
	(1, 1), (1, 2), (1, 3), (1, 4),
	(2, 5), (2, 6), (2, 7),
	(3, 8), (3, 9),
	(4, 10), (4, 11),
	(5, 12), (5, 13), (5, 14), (5, 15), (5, 16), (5, 17),
	(6, 18), (6, 19);
	
INSERT INTO sessions_speakers (SESSION_ID, SPEAKER_ID) VALUES
	(1, 1),
	(2, 2),
	(3, 2), (3, 3),
	(4, 3),
	(5, 4),
	(6, 5),
	(7, 6),
	(8, 7),
	(9, 8),
	(10, 9),
	(11, 9),
	(12, 9),
	(13, 11),
	(14, 11), (14, 12),
	(15, 11), (15, 12),
	(16, 13),
	(17, 13),
	(18, 14),
	(19, 14);