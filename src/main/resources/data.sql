
insert into restaurant(id,name,tags) values
 ('r_kitchen','Rocket Kitchen','burgers,fries'),
 ('sushi_go','Sushi Go','sushi,japanese');

insert into menu_item(id,restaurant_id,name,price) values
 ('m_burger','r_kitchen','Classic Burger',5.99),
 ('m_fries','r_kitchen','Fries',2.49),
 ('m_roll','sushi_go','Salmon Roll',6.49);

