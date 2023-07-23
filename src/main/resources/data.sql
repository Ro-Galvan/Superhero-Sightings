use herosightings;
-- use herosightingstest;
select * from hero;
select * from heroorganization;
select * from location;
select * from organization;
select * from power;
select * from sighting;

insert into power (powerPK, power, description)
values
(1, 'Super Powers', 'Super speed, strength and flying'),
(2, 'Magical', 'Can cast spells and control the elements'),
(4, 'Invisibility', 'Becomes invisible at will'),
(6, 'Timey Wimey', 'Can manipulate time.  Make it slower or faster.');

insert into hero (heroPK, heroName, type, description, powerPK)
values
(4, 'Mighty Man', 'Hero', 'The mightiest of all', 1),
(5, 'Wizard Warrior', 'Hero', 'Very quick with the spell', 2),
(7, 'Dr Who', 'Hero', 'Has a TARDIS and travels thru time', 6),
(8, 'The Master', 'Villain', 'Bad guy with his own TARDIS', 6),
(10, 'Super Chaos', 'Villain', 'Just another bad guy who can be invisible', 4);

INSERT INTO organization (organizationPK, organizationName, type, description, organizationAddress, phone, contactInfo)
VALUES
(1, 'Justice Federation', 'Hero', 'Heros for justice', '555 Main St, Kingsplace, NY', '222-333-4455', 'Head Honcho'),
(2, 'Freedom Leauge', 'Hero', 'Freedom for all', '123 Harvard Ave, Queensplace, NY', '444-222-5533', 'Mr Davis'),
(3, 'Chaos Charter', 'Villian', 'Creators of chaos', '777 Park Pl, Jokersplace, NY', '222-888-9955', 'Mr Urkel'),
(5, 'The Bad Guys', 'Villian', 'All around bad guys doing bad things', '2224 Hyjinks Ave, Gremlinsplace, NY', '222-777-8899', 'Mr Hyjinks'),
(6, 'The Hero Longhouse', 'Hero', 'Group of viking heros', '104 Viking Way, Norsplace, NY', '222-555-1133', 'Odin');

INSERT INTO location(locationPK, locationName, description, locationAddress, latitude, longitude)
VALUES
(1, 'Downtown Main', 'Downtown in Kingsplace by Plaza', '123 Main St, Kingsplace, NY',
'40.71455', '-74.00712'),
(2, 'University Central', 'On campus by Central Ave', '88 Central Ave, Queensplace, NY',
'33.77391', '-84.53921'),
(3, 'The Duckpond', 'The Duckpond in the middle of city park', '11 University Ave, Kingsplace, NY',
'44.43777', '-63.63548'),
(4, 'The Plaza', 'Center of  City Park', '456 Plaza Way, Longsplace, NY',
'51.88423', '-97.39123');

INSERT INTO heroorganization(heroPK, organizationPK)
VALUES
(4, 1),
(5, 1),
(7, 1),
(5, 2),
(4, 2),
(7, 2),
(8, 3),
(10, 3),
(8, 5),
(10, 5);




insert into sighting (sightingPK, sightingDate, description, heroPK, locationPK)
values
(1, '2022-01-01 10:15:00', 'Did something heroic', 4, 1),
(2, '2022-01-01 11:30:00', 'Did something heroic', 5, 2),
(3, '2022-01-01 22:05:00', 'Did something evil', 10, 3),
(4, '2022-02-01 10:15:00', 'Did something heroic', 7, 4),
(5, '2022-02-01 23:45:00', 'Did something evil', 8, 4),
(6, '2022-02-01 09:15:00', 'Did something heroic', 5, 3),
(7, '2022-03-01 08:30:00', 'Did something heroic', 4, 4),
(8, '2022-03-01 21:15:00', 'Did something heroic', 7, 1),
(9, '2022-03-01 22:45:00', 'Did something evil', 8, 1);