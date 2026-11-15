-- ILLUSTRATIVE DEVELOPMENT SEED.
-- The specification's approved V2__seed.sql attachment was not supplied.
-- These rows are sample content only; replace this migration with the approved dataset
-- before production. The schema and API are independent of the particular seed content.
INSERT INTO cities (id,name,country,lat,lng,timezone,hue,blurb,description) VALUES
('paris','Paris','France',48.8566,2.3522,'Europe/Paris',335,'Art, food and timeless streets','Paris is known for its museums, architecture and neighbourhoods.'),
('tokyo','Tokyo','Japan',35.6762,139.6503,'Asia/Tokyo',210,'Tradition meets modern life','Tokyo blends historic temples with modern districts.'),
('new-york','New York','United States',40.7128,-74.0060,'America/New_York',25,'A city of iconic neighbourhoods','New York offers museums, parks, food and live culture.'),
('lagos','Lagos','Nigeria',6.5244,3.3792,'Africa/Lagos',165,'Energy, culture and coastal life','Lagos is a major centre for Nigerian arts, food and business.');

INSERT INTO places (id,city_id,category,name,description,address,phone,website,lat,lng,opens_at,closes_at,open_24h,closed_days,hours_text,extra,base_rating,rating_avg,review_count) VALUES
('eiffel','paris','attraction','Eiffel Tower','Landmark tower with city views.','Champ de Mars','—','https://www.toureiffel.paris',48.8584,2.2945,'09:30','23:45',false,NULL,'Daily 09:30–23:45','Tickets required',4.8,4.8,0),
('louvre','paris','attraction','Louvre Museum','Major art museum.','Rue de Rivoli','—','https://www.louvre.fr',48.8606,2.3376,'09:00','18:00',false,ARRAY[2]::smallint[],'Wed–Mon 09:00–18:00','Tickets required',4.7,4.7,0),
('notre-dame','paris','attraction','Notre-Dame Cathedral','Historic cathedral on Île de la Cité.','6 Parvis Notre-Dame','—','https://www.notredamedeparis.fr',48.8530,2.3499,'08:00','19:00',false,NULL,'Daily 08:00–19:00','Check visitor access',4.7,4.7,0),
('montmartre','paris','attraction','Montmartre','Hilltop district with art and city views.','Montmartre','—','',48.8867,2.3431,NULL,NULL,false,NULL,'Open area','Free to explore',4.6,4.6,0),
('cafe-de-flore','paris','restaurant','Café de Flore','Historic Paris café.','172 Boulevard Saint-Germain','—','https://cafedeflore.fr',48.8540,2.3325,'07:30','01:30',false,NULL,'Daily 07:30–01:30','Food and drinks',4.2,4.2,0),
('bouillon-chartier','paris','restaurant','Bouillon Chartier','Traditional French dining room.','7 Rue du Faubourg Montmartre','—','https://www.bouillon-chartier.com',48.8730,2.3420,'11:30','00:00',false,NULL,'Daily 11:30–00:00','French cuisine',4.1,4.1,0),
('hotel-lutetia','paris','hotel','Hôtel Lutetia','Historic hotel on the Left Bank.','45 Boulevard Raspail','—','https://www.hotellutetia.com',48.8510,2.3260,NULL,NULL,true,NULL,'Open 24 hours','Luxury stay',4.5,4.5,0),
('hotel-crillon','paris','hotel','Hôtel de Crillon','Luxury hotel overlooking Place de la Concorde.','10 Place de la Concorde','—','https://www.rosewoodhotels.com',48.8675,2.3212,NULL,NULL,true,NULL,'Open 24 hours','Luxury stay',4.6,4.6,0),
('paris-jazz','paris','event','Paris Jazz Nights','Live jazz event listing.','Various venues','—','',48.8566,2.3522,NULL,NULL,false,NULL,'Check event schedule','Seasonal event',4.3,4.3,0),
('sensoji','tokyo','attraction','Sensō-ji','Historic Buddhist temple in Asakusa.','2 Chome-3-1 Asakusa','—','https://www.senso-ji.jp',35.7148,139.7967,'06:00','17:00',false,NULL,'Daily 06:00–17:00','Temple grounds',4.7,4.7,0),
('tokyo-skytree','tokyo','attraction','Tokyo Skytree','Observation tower with city views.','1 Chome-1-2 Oshiage','—','https://www.tokyo-skytree.jp',35.7101,139.8107,'10:00','21:00',false,NULL,'Daily 10:00–21:00','Ticketed attraction',4.5,4.5,0),
('meiji-jingu','tokyo','attraction','Meiji Jingu','Shinto shrine set in a forest.','1-1 Yoyogikamizonocho','—','https://www.meijijingu.or.jp',35.6764,139.6993,'05:00','18:00',false,NULL,'Hours vary by season','Shrine grounds',4.7,4.7,0),
('ueno-park','tokyo','attraction','Ueno Park','Large public park with museums.','Uenokoen','—','',35.7156,139.7745,NULL,NULL,false,NULL,'Open area','Public park',4.4,4.4,0),
('ichiran-shibuya','tokyo','restaurant','Ichiran Shibuya','Japanese ramen restaurant.','1 Chome-22-7 Jinnan','—','https://ichiran.com',35.6615,139.6993,'10:00','06:00',false,NULL,'Daily 10:00–06:00','Ramen',4.3,4.3,0),
('sushi-dai','tokyo','restaurant','Sushi Dai','Sushi restaurant at Toyosu Market.','6 Chome-5-1 Toyosu','—','',35.6454,139.7840,'05:00','14:00',false,NULL,'Daily 05:00–14:00','Sushi',4.5,4.5,0),
('park-hyatt-tokyo','tokyo','hotel','Park Hyatt Tokyo','Luxury hotel in Shinjuku.','3-7-1-2 Nishi-Shinjuku','—','https://www.hyatt.com',35.6850,139.6907,NULL,NULL,true,NULL,'Open 24 hours','Luxury stay',4.6,4.6,0),
('granbell-shinjuku','tokyo','hotel','Shinjuku Granbell Hotel','Design hotel in Shinjuku.','2 Chome-14-5 Kabukicho','—','https://www.granbellhotel.jp',35.6960,139.7040,NULL,NULL,true,NULL,'Open 24 hours','City hotel',4.2,4.2,0),
('tokyo-sakura','tokyo','event','Tokyo Sakura Season','Seasonal cherry blossom viewing.','Various parks','—','',35.6762,139.6503,NULL,NULL,false,NULL,'Seasonal','Seasonal event',4.8,4.8,0),
('centralpark','new-york','attraction','Central Park','Large urban park in Manhattan.','Central Park','—','https://www.centralparknyc.org',40.7829,-73.9654,NULL,NULL,false,NULL,'Open area','Public park',4.8,4.8,0),
('statue-liberty','new-york','attraction','Statue of Liberty','Monument on Liberty Island.','Liberty Island','—','https://www.nps.gov/stli',40.6892,-74.0445,'09:00','17:00',false,NULL,'Daily 09:00–17:00','Ferry ticket required',4.7,4.7,0),
('met-museum','new-york','attraction','The Metropolitan Museum of Art','Art museum on Fifth Avenue.','1000 Fifth Avenue','—','https://www.metmuseum.org',40.7794,-73.9632,'10:00','17:00',false,ARRAY[3]::smallint[],'Hours vary; closed Wednesday','Suggested admission',4.8,4.8,0),
('times-square','new-york','attraction','Times Square','Busy commercial and entertainment district.','Manhattan','—','',40.7580,-73.9855,NULL,NULL,false,NULL,'Open area','Public space',4.4,4.4,0),
('katz-delicatessen','new-york','restaurant','Katz’s Delicatessen','Famous New York deli.','205 E Houston Street','—','https://www.katzsdelicatessen.com',40.7223,-73.9874,'08:00','22:45',false,NULL,'Daily 08:00–22:45','Deli',4.5,4.5,0),
('levain-bakery','new-york','restaurant','Levain Bakery','Bakery known for cookies.','167 W 74th Street','—','https://levainbakery.com',40.7790,-73.9780,'08:00','20:00',false,NULL,'Daily 08:00–20:00','Bakery',4.6,4.6,0),
('the-plaza','new-york','hotel','The Plaza','Landmark luxury hotel.','768 Fifth Avenue','—','https://www.theplazany.com',40.7645,-73.9745,NULL,NULL,true,NULL,'Open 24 hours','Luxury stay',4.5,4.5,0),
('the-standard-high-line','new-york','hotel','The Standard High Line','Hotel in the Meatpacking District.','848 Washington Street','—','https://www.standardhotels.com',40.7409,-74.0080,NULL,NULL,true,NULL,'Open 24 hours','Design hotel',4.3,4.3,0),
('broadway-week','new-york','event','Broadway Week','Seasonal theatre promotion.','Broadway','—','',40.7590,-73.9845,NULL,NULL,false,NULL,'Seasonal','Theatre event',4.4,4.4,0),
('lekki-conservation','lagos','attraction','Lekki Conservation Centre','Nature reserve with canopy walkway.','Lekki-Epe Expressway','—','https://ncfnigeria.org',6.4378,3.5350,'08:30','17:00',false,NULL,'Daily 08:30–17:00','Entry fee',4.3,4.3,0),
('nike-art-gallery','lagos','attraction','Nike Art Gallery','Gallery featuring Nigerian art.','2 Elegushi Road','—','',6.4350,3.4730,'10:00','18:00',false,NULL,'Daily 10:00–18:00','Art gallery',4.5,4.5,0),
('freedom-park','lagos','attraction','Freedom Park','Arts and culture centre on Lagos Island.','1 Hospital Road','—','',6.4510,3.3950,'10:00','18:00',false,NULL,'Hours vary by event','Culture venue',4.2,4.2,0),
('yellow-chilli','lagos','restaurant','Yellow Chilli','Restaurant serving Nigerian dishes.','27 Oju Olobun Close','—','',6.4310,3.4210,'12:00','22:00',false,NULL,'Daily 12:00–22:00','Nigerian cuisine',4.2,4.2,0),
('hard-rock-lagos','lagos','restaurant','Hard Rock Cafe Lagos','Restaurant and live music venue.','Landmark Centre, Victoria Island','—','https://www.hardrockcafe.com',6.4300,3.4210,'12:00','00:00',false,NULL,'Daily 12:00–00:00','Dining and music',4.1,4.1,0),
('radisson-blu-lagos','lagos','hotel','Radisson Blu Anchorage Hotel','Waterfront hotel on Victoria Island.','1A Ozumba Mbadiwe Avenue','—','https://www.radissonhotels.com',6.4250,3.4380,NULL,NULL,true,NULL,'Open 24 hours','Waterfront hotel',4.3,4.3,0),
('eko-hotels','lagos','hotel','Eko Hotels and Suites','Large hotel and conference complex.','Plot 1415 Adetokunbo Ademola Street','—','https://www.ekohotels.com',6.4280,3.4210,NULL,NULL,true,NULL,'Open 24 hours','Hotel and suites',4.2,4.2,0),
('lagos-art-week','lagos','event','Lagos Art Week','Contemporary art exhibitions and events.','Various galleries','—','',6.5244,3.3792,NULL,NULL,false,NULL,'Annual event','Seasonal',4.4,4.4,0);
