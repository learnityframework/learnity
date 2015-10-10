
SET GLOBAL max_connections=5000;
SET GLOBAL max_allowed_packet=5000*1024*1024;


/*********************** admin_user ********************************/

drop table if exists admin_user ;
create table admin_user
(
  user_id 		varchar(25) not null,
  password 		varchar(25),
  
  primary key(user_id)
) TYPE=INNODB; 

insert into admin_user values('superadmin','superadmin');

/******************** administrator_login_time *********************/

drop table if exists administrator_login_time ;

create table administrator_login_time
(
  user_id 			varchar(25) not null,
  login_time 		datetime,
  primary key(user_id)  
) TYPE=INNODB;


/******************** administrator_details ************************/

drop table if exists administrator_details ;

create table administrator_details
(
  user_id 					varchar(25) not null,
  user_first_name				varchar(50) not null,
  user_middle_name			varchar(50),
  user_last_name				varchar(50) not null,
  gender					varchar(8),
  email_id 					varchar(25),	
  department 				varchar(25),
  educational_qualification 		varchar(25),
  age 					varchar(2),
  account_status 				varchar(25),
  experience				varchar(10),
  access_priviledge			varchar(10),

  primary key(user_id)    
) TYPE=INNODB;

insert into administrator_details values ('superadmin','superadmin','','superadmin','','','','','0','Active','0.0','T');

/****************** administrator_creation_details *****************/

drop table if exists administrator_creation_details ;

create table administrator_creation_details
(
  user_id 					varchar(25) not null,
  date_user_created 			datetime,
  user_created_by 			varchar(25),
  last_modification_date 		datetime,
  primary key(user_id)    
) TYPE=INNODB;

insert into administrator_creation_details values ('superadmin',sysdate(),'superadmin',sysdate());



/****************** administrator_previlege_management *************/

drop table if exists administrator_previlege_management ;

create table administrator_previlege_management
(
  user_id					varchar(25) not null,
  previledge				varchar(35) not null,
  primary key(user_id)    
) TYPE=INNODB;

insert into administrator_previlege_management values('superadmin','TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT');

/******************* administrator_logged_in_at ********************/

drop table if exists administrator_logged_in_at ;

create table administrator_logged_in_at
(
  user_id 		varchar(25) not null,
  logged_in_at    datetime
) TYPE=INNODB;

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` int(10) NOT NULL auto_increment,
  `title` varchar(100) default NULL,
  `description` varchar(100) default NULL,
  PRIMARY KEY  (`role_id`)
) TYPE=InnoDB;
create INDEX role1 on role(role_id);

-- DROP TABLE IF EXISTS `user_role`;
-- 
-- CREATE TABLE `user_role` (
--   `user_id` varchar(100) NOT NULL default '',
--   `role_id` int(10) NOT NULL default '0',
--   `comment` varchar(100) default NULL,
--   `date` datetime default NULL,
--   `entered_by` varchar(100) default NULL,
--   PRIMARY KEY  (`role_id`,`user_id`)
-- ) TYPE=InnoDB;
-- 
-- create INDEX user_role1 on user_role(user_id,role_id);


DROP TABLE IF EXISTS interface;
CREATE TABLE interface (
  interface_id varchar(100) NOT NULL ,
  interface_title varchar(100) default NULL,
  type              varchar(100),
  filesize          varchar(100),
  
  PRIMARY KEY  (interface_id)
)Type=InnoDB;
create INDEX interface1 on interface(interface_id);

DROP TABLE IF EXISTS structure;
CREATE TABLE structure (
  interface_id  varchar(100) NOT NULL ,
  part_id  varchar(100) default NULL,
  part_class  varchar(100) NOT NULL ,
  resize  varchar(100) default ' ' NOT NULL ,
  border  varchar(100)  default ' ' NOT NULL ,
  cols  varchar(100) default ' ' NOT NULL ,
  rows  varchar(100) default ' ' NOT NULL ,
  scrolling  varchar(100) default ' ' NOT NULL ,
  spacing  varchar(100) default ' ' NOT NULL ,
  colspan  varchar(100) default ' ' NOT NULL ,
  length  varchar(100) default ' ' NOT NULL ,
  size  varchar(100) default ' ' NOT NULL ,
  tabindex  varchar(100) default ' ' NOT NULL ,
  archieve   varchar(100) default ' ' NOT NULL ,
  codebase  varchar(100) default ' ' NOT NULL ,
  mayscript  varchar(100) default ' ' NOT NULL ,
  loadurl       varchar(200) default ' ' NOT NULL ,
  editurl       varchar(200) default ' ' NOT NULL ,
  caption       varchar(100) default ' ' NOT NULL ,
  sortname       varchar(300) default ' ' NOT NULL ,
  sortorder       varchar(100) default ' ' NOT NULL ,
  gridhidden     varchar(100) default ' ' NOT NULL ,
  gridnavbar     varchar(100) default ' ' NOT NULL ,
  multiselect      varchar(100),
  multiboxonly      varchar(100),
  gridrowNum       varchar(100),
  gridrowList      varchar(100),   
  dateformat       varchar(200),
  resetSearchOnClose      varchar(100),
  multiplesearch      varchar(100),
  customeditbutton    varchar(100),
  griddata    varchar(100),
  griddatatype    varchar(100),
  PRIMARY KEY  (interface_id,part_id)
)Type=InnoDB;
create INDEX structure1 on structure(interface_id,part_id);

DROP TABLE IF EXISTS layout;
CREATE TABLE layout (
  layout_interface_id  int(10) NOT NULL auto_increment,
  interface_id  varchar(100) NOT NULL ,
  layout_id  varchar(100)  ,
  part_id  varchar(100) default NULL,
  height  varchar(100) default NULL,
  width  varchar(100) NOT NULL ,
  x  varchar(100) default NULL,
  y  varchar(100) NOT NULL ,
  position  varchar(100) default NULL,
  parent_id  varchar(100) NOT NULL ,
 
  PRIMARY KEY  (layout_interface_id)
)Type=InnoDB;
create INDEX layout1 on layout(interface_id,layout_id);

DROP TABLE IF EXISTS content;
CREATE TABLE content (
  menu_item_id mediumint not null auto_increment,
  interface_id  varchar(100) NOT NULL ,
  content_id  varchar(100)  ,
  part_id  varchar(100) default NULL,
  value  longblob  ,
  contenttype  varchar(100) default NULL,
  active_value  longblob  ,
  contentlocation  varchar(100),
  show_choose_one varchar(100),
  choose_one_label varchar(200),
  choose_one_value varchar(200),
   
  PRIMARY KEY  (menu_item_id,interface_id,content_id,part_id)
)Type=InnoDB;
create INDEX content1 on content(interface_id,content_id,part_id);

DROP TABLE IF EXISTS style;
CREATE TABLE style (
  interface_id  varchar(100) NOT NULL ,
  style_id  varchar(100)  ,
  part_id  varchar(100) default NULL,
  value  blob ,
  styletype  varchar(100) default NULL,
   PRIMARY KEY  (interface_id,style_id,part_id)
)Type=InnoDB;

create INDEX style1 on style(interface_id,style_id);

DROP TABLE IF EXISTS behaviour;
CREATE TABLE behaviour (
  event_id int(10) NOT NULL auto_increment,
  interface_id  varchar(100)  ,
  behaviour_id  varchar(100) ,
  part_id  varchar(100) ,
  value  varchar(100)  ,
  valuetype  varchar(100) ,
  target  varchar(100),
  behaviourevent  varchar(100)  ,
  parameter  varchar(100) ,
  type  varchar(100)  ,
  callback  varchar(100)  ,
  resourceid  varchar(100) ,
  javaclass  varchar(100) ,
  package  varchar(100) ,
  resource_location  varchar(100),
  dom_ready  varchar(300),

  PRIMARY KEY  (event_id,interface_id,behaviour_id,part_id)
)Type=InnoDB;

create INDEX behaviour1 on behaviour(interface_id,behaviour_id,part_id);


DROP TABLE IF EXISTS resource;
CREATE TABLE resource (
  interface_id  varchar(100) NOT NULL ,
  resource_id  varchar(100) default NULL,
  value longblob ,
  href  varchar(100) default NULL,
  type  varchar(100) default NULL,
  keyvalue  varchar(100),
  resource_location  varchar(100),
   PRIMARY KEY  (interface_id,resource_id)
)Type=InnoDB;

create INDEX resource1 on resource(interface_id,resource_id);

DROP TABLE IF EXISTS snippet;
CREATE TABLE snippet (
  interface_id  varchar(100) NOT NULL ,
  snippet_id  varchar(100) default NULL,
  value longblob ,
  PRIMARY KEY  (interface_id,snippet_id)
)Type=InnoDB;

create INDEX snippet1 on snippet(interface_id,snippet_id);

DROP TABLE IF EXISTS resource_type;
CREATE TABLE resource_type (
  resource_type_id  varchar(100) NOT NULL ,
  resource_type_title  varchar(100) default NULL,
  PRIMARY KEY  (resource_type_id)
)Type=InnoDB;
insert into resource_type(resource_type_id,resource_type_title) values('Interfacexml','Interfacexml');
insert into resource_type(resource_type_id,resource_type_title) values('Resources','Resources');

DROP TABLE IF EXISTS manifestinterfaceassociation;
CREATE TABLE manifestinterfaceassociation (
  manifest_id  varchar(100)  ,
  interface_id  varchar(100) ,
  PRIMARY KEY  (manifest_id,interface_id)
)Type=InnoDB;

create INDEX manifestinterfaceassociation1 on manifestinterfaceassociation(interface_id,manifest_id);


DROP TABLE IF EXISTS roleassociation;
CREATE TABLE roleassociation (
   interface_id varchar(100) ,
   role_id varchar(100) NOT NULL ,
   layout_id varchar(100) ,
   style_id varchar(100) ,
   content_id varchar(100) ,
   behaviour_id varchar(100) ,
  PRIMARY KEY  (role_id,interface_id)
)Type=InnoDB;
create INDEX roleassociation1 on roleassociation(interface_id,role_id,layout_id,style_id,content_id,behaviour_id);


DROP TABLE IF EXISTS optionmenu;
CREATE TABLE optionmenu (
   interface_id varchar(100) ,
   part_id varchar(100) NOT NULL ,
   option_id varchar(100) NOT NULL ,
   labelname varchar(100) ,
   labelvalue varchar(100) ,
   PRIMARY KEY  (interface_id,part_id,option_id)
)Type=InnoDB;
create INDEX optionmenu1 on optionmenu(interface_id,part_id);


DROP TABLE IF EXISTS columnmodel;
CREATE TABLE columnmodel(
   interface_id  varchar(100) NOT NULL,
   part_id   varchar(100) NOT NULL ,
   col_head   varchar(100) ,
   col_name  varchar(100)  NOT NULL,
   col_index  int ,
   col_width  varchar(100)  ,
   col_editable varchar(100)  ,
  col_hidden varchar(100)  ,
  key_value varchar(100),
  required    varchar(100),
  minval     varchar(100),
  maxval    varchar(100),
  dbcolumnname    varchar(200),
  edithidden    varchar(200),
  influence    varchar(100),
  email    varchar(300),
  number_check    varchar(300),
  custom    varchar(300),
  custom_func    varchar(300),
  default_type    varchar(200),
  default_value    varchar(200),
  
      PRIMARY KEY  (interface_id,part_id,col_name)
)Type=InnoDB;

create INDEX columnmodel1 on columnmodel(interface_id,part_id);

DROP TABLE IF EXISTS gridquery;
CREATE TABLE gridquery(
   interface_id  varchar(100),
   part_id   varchar(100)  ,
   grid_query       blob,
   active_query   blob,
   load_parameter        blob,
   server_cache_grid      varchar(10) default ' ' NOT NULL ,
   cachekey_includeuserid varchar(10) default ' ' NOT NULL ,
   
   PRIMARY KEY  (interface_id,part_id)
)Type=InnoDB;

create INDEX gridquery1 on gridquery(interface_id,part_id);

DROP TABLE IF EXISTS keycolumn;
CREATE TABLE keycolumn(
   interface_id  varchar(100),
   part_id   varchar(100) ,
   keycolumn_value    varchar(250),
   PRIMARY KEY  (interface_id,part_id)
)Type=InnoDB;
create INDEX keycolumn1 on keycolumn(interface_id,part_id);

DROP TABLE IF EXISTS delete_param;
CREATE TABLE delete_param(
   interface_id  varchar(100),
   part_id   varchar(100)  ,
   query_id   varchar(100)  ,
   delete_param_value   blob,
   delete_parameter    blob,
   PRIMARY KEY  (interface_id,part_id,query_id)
)Type=InnoDB;

create INDEX delete_param1 on delete_param(interface_id,part_id);

DROP TABLE IF EXISTS add_param;
CREATE TABLE add_param(
   interface_id  varchar(100),
   part_id   varchar(100)  ,
   query_id   varchar(100)  ,
   add_param_value   blob,
   add_parameter    blob,
   PRIMARY KEY  (interface_id,part_id,query_id)
)Type=InnoDB;

create INDEX add_param1 on add_param(interface_id,part_id);

DROP TABLE IF EXISTS modify_param;
CREATE TABLE modify_param(
   interface_id  varchar(100),
   part_id   varchar(100)  ,
     query_id   varchar(100)  ,
   modify_param_value    blob,
   modify_parameter        blob,
   PRIMARY KEY  (interface_id,part_id,query_id)
)Type=InnoDB;
create INDEX modify_param1 on modify_param(interface_id,part_id);

DROP TABLE IF EXISTS edit_type;
CREATE TABLE edit_type(
   interface_id  varchar(100),
   part_id   varchar(100)  ,
   col_name  varchar(100)  ,
   type varchar(100),
   size  varchar(100),
   rows varchar(100),
   cols  varchar(100),
   editdomaintype varchar(100),
   value              blob,
   active_value             blob,
   multiple    varchar(200),
   PRIMARY KEY  (interface_id,part_id,col_name)
)Type=InnoDB;
create INDEX edit_type1 on edit_type(interface_id,part_id);

DROP TABLE IF EXISTS tree_structure;
CREATE TABLE tree_structure(
   interface_id  varchar(100),
   part_id   varchar(100)  ,
   treedataremotefunction      varchar(100),
   onselectremotefunction   varchar(100),
   autocollapse   varchar(100),
   initialiseonload   varchar(100),
   islazynode varchar(100),
   tooltip varchar(100),
   nodestructure  blob,
   parentsql  blob,
   childnodesql   blob,
   tree_parameter        blob,
   PRIMARY KEY  (interface_id,part_id)
)Type=InnoDB;
create INDEX tree_structure1 on tree_structure(interface_id,part_id);

DROP TABLE IF EXISTS selector;
CREATE TABLE selector (
     interface_id  varchar(100),
     part_id   varchar(100),
     selector_id varchar(100) ,
     gridrefresh  varchar(100),
     influence    varchar(100),
     PRIMARY KEY  (interface_id,selector_id,part_id)
)Type=InnoDB;

create INDEX selector1 on selector(interface_id,part_id);

DROP TABLE IF EXISTS delete_validation_param;
CREATE TABLE delete_validation_param(
   interface_id  varchar(100),
   part_id   varchar(100)  ,
   query_id   varchar(100)  ,
   delete_param_value   blob,
   delete_parameter    blob,
   message                varchar(200),
   type                       varchar(100),
   function_name        varchar(100),
   PRIMARY KEY  (interface_id,part_id,query_id)
)Type=InnoDB;

create INDEX delete_validation_param1 on delete_validation_param(interface_id,part_id);

DROP TABLE IF EXISTS add_validation_param;
CREATE TABLE add_validation_param(
   interface_id  varchar(100),
   part_id   varchar(100)  ,
   query_id   varchar(100)  ,
   add_param_value   blob,
   add_parameter    blob,
   message                varchar(200),
    type                       varchar(100),
   function_name        varchar(100),
   PRIMARY KEY  (interface_id,part_id,query_id)
)Type=InnoDB;

create INDEX  add_validation_param1 on add_validation_param(interface_id,part_id);

DROP TABLE IF EXISTS modify_validation_param;
CREATE TABLE modify_validation_param(
   interface_id  varchar(100),
   part_id   varchar(100)  ,
   query_id   varchar(100)  ,
   modify_param_value   blob,
   modify_parameter   blob,
   message                varchar(200),
    type                       varchar(100),
   function_name        varchar(100),
   PRIMARY KEY  (interface_id,part_id,query_id)
)Type=InnoDB;
create INDEX  modify_validation_param1 on modify_validation_param(interface_id,part_id);


DROP TABLE IF EXISTS dropdownmenu;
CREATE TABLE dropdownmenu (
   menu_item_id mediumint not null auto_increment,
   interface_id varchar(100) ,
   part_id varchar(100) NOT NULL ,
   name varchar(300) ,
   value varchar(300) ,
   PRIMARY KEY  (menu_item_id)
)Type=InnoDB;

create INDEX  dropdownmenu1 on dropdownmenu(interface_id,part_id);


drop table if exists add_action_param;

create table add_action_param(
interface_id             varchar(100),
part_id                  varchar(100),
action_sequence          varchar(10),
action_name              varchar(200),
primary key(interface_id,part_id)
)TYPE=InnoDB;

create INDEX  add_action_param1 on add_action_param(interface_id,part_id);
drop table if exists modify_action_param;

create table modify_action_param(
interface_id             varchar(100),
part_id                  varchar(100),
action_sequence          varchar(10),
action_name              varchar(200),
primary key(interface_id,part_id)
)TYPE=InnoDB;

create INDEX  modify_action_param1 on modify_action_param(interface_id,part_id);
drop table if exists delete_action_param;

create table delete_action_param(
interface_id             varchar(100),
part_id                  varchar(100),
action_sequence          varchar(10),
action_name              varchar(200),
primary key(interface_id,part_id)
)TYPE=InnoDB;
create INDEX  delete_action_param1 on delete_action_param(interface_id,part_id);

drop table if exists configuration_item;

create table configuration_item(
interface_id             varchar(100),
createsession          varchar(100),
checkrole                varchar(100),
contenttype             varchar(100),
doctypepublic                  varchar(100),
doctypesystem                  varchar(100),
cachecontrol                  varchar(300),
expires                  varchar(300),
lastmodify                  varchar(300),
template                   varchar(300),
themes_id          varchar(200),
enable_caching     varchar(10) default ' ' NOT NULL ,
cache_name         varchar(200),
cache_dynamic_js     varchar(10) default ' ' NOT NULL ,
cache_dynamic_css     varchar(10) default ' ' NOT NULL ,
cache_dynamic_image     varchar(10) default ' ' NOT NULL ,
primary key(interface_id)
)TYPE=InnoDB;
create INDEX  configuration_item1 on configuration_item(interface_id);


DROP TABLE IF EXISTS `framework_file`;

CREATE TABLE `framework_file` (
  `framework_file_id` varchar(100) NOT NULL,
  `framework_file_title` varchar(100) default NULL,
  `type` varchar(100) default NULL,
  `filesize` varchar(100) default NULL,
  `filename` varchar(100) default NULL,
   inlinecss varchar(100),
   inlinejs  varchar(100),
   imagepath varchar(100),
  `value` longblob,
  last_updated    datetime,
  PRIMARY KEY  (`framework_file_id`)

) TYPE=InnoDB;

create INDEX  framework_file1 on framework_file(framework_file_id);

DROP TABLE IF EXISTS formelement;
CREATE TABLE formelement (
  interface_id  varchar(100) NOT NULL,
  part_id  varchar(100) default NULL,
  formmethod varchar(100) default NULL,
  formaction varchar(100) default NULL,
  jscontrol    varchar(100) default NULL,
  PRIMARY KEY  (interface_id,part_id)
 ) TYPE=InnoDB;

create INDEX  formelement1 on formelement(interface_id,part_id);

DROP TABLE IF EXISTS interfaceenginecalling;
CREATE TABLE interfaceenginecalling (
  interface_id  varchar(100) NOT NULL ,
  valueblob longblob ,
  layout_id  varchar(100) default NULL, 
   content_id  varchar(100) default NULL, 
    behaviour_id  varchar(100) default NULL, 
     style_id  varchar(100) default NULL, 
   PRIMARY KEY  (interface_id,layout_id,content_id,behaviour_id,style_id)
)Type=InnoDB;
create INDEX  interfaceenginecalling1 on interfaceenginecalling(interface_id,layout_id,content_id,behaviour_id,style_id);


 
DROP TABLE IF EXISTS form_delete_param;
CREATE TABLE form_delete_param(
   interface_id  varchar(100),
   part_id   varchar(100)  ,
   query_id   varchar(100)  ,
   delete_param_value   blob,
   delete_parameter    blob,
   PRIMARY KEY  (interface_id,part_id,query_id)
)Type=InnoDB;

create INDEX form_delete_param1 on form_delete_param(interface_id,part_id);

DROP TABLE IF EXISTS form_add_param;
CREATE TABLE form_add_param(
   interface_id  varchar(100),
   part_id   varchar(100)  ,
   query_id   varchar(100)  ,
   add_param_value   blob,
   add_parameter    blob,
   PRIMARY KEY  (interface_id,part_id,query_id)
)Type=InnoDB;

create INDEX form_add_param1 on form_add_param(interface_id,part_id);

DROP TABLE IF EXISTS form_modify_param;
CREATE TABLE form_modify_param(
   interface_id  varchar(100),
   part_id   varchar(100)  ,
     query_id   varchar(100)  ,
   modify_param_value    blob,
   modify_parameter        blob,
   PRIMARY KEY  (interface_id,part_id,query_id)
)Type=InnoDB;
create INDEX form_modify_param1 on form_modify_param(interface_id,part_id);


DROP TABLE IF EXISTS form_element;
CREATE TABLE form_element(
   interface_id  varchar(100),
   part_id   varchar(100)  ,
   element_id   varchar(100)  ,
   element_type    varchar(100),
   element_key    varchar(100),
   selectindex    int DEFAULT 0,
   modifyindex    int DEFAULT 0,
   insertindex    int DEFAULT 0,
   forlabel       varchar(100),
   required       varchar(100),
   minlength      varchar(50),
   maxlength      varchar(50),
   equalto        varchar(50),
   numbercheck    varchar(50),
   email          varchar(50), 
   requiredmess       varchar(200),
   minlengthmess      varchar(200),
   maxlengthmess      varchar(200),
   equaltomess        varchar(200),
   numbercheckmess    varchar(200),
   emailmess          varchar(200), 
   
   PRIMARY KEY  (interface_id,part_id,element_id)
)Type=InnoDB;
create INDEX form_element1 on form_element(interface_id,part_id,element_id);


DROP TABLE IF EXISTS form_select_param;
CREATE TABLE form_select_param(
   interface_id  varchar(100),
   part_id   varchar(100)  ,
   query_id   varchar(100)  ,
   select_param_value   blob,
   select_parameter    blob,
   PRIMARY KEY  (interface_id,part_id,query_id)
)Type=InnoDB;
create INDEX form_select_param1 on form_select_param(interface_id,part_id);




/*   REPORT AND CHART SCHEMA ADD                                           */
DROP TABLE IF EXISTS interface_chart_content;
CREATE TABLE  `interface_chart_content` (
  `interface_chart_item_id` mediumint(9) NOT NULL auto_increment,
  `interface_id` varchar(100) NOT NULL,
  `content_id` varchar(100) NOT NULL default '',
  `part_id` varchar(100) NOT NULL default '',
  `metric_title` varchar(100) default NULL,
  `description` varchar(200) default NULL,
  `X_axis_query` blob,
  `series1_query` blob,
  `series2_query` blob,
  `series3_query` blob,
  `legenddata1` varchar(50) default NULL,
  `legenddata2` varchar(50) default NULL,
  `legenddata3` varchar(50) default NULL,
  `chart_type` varchar(50) default NULL,
  `subtype` varchar(50) default NULL,
  `width` int(6) NOT NULL default '0',
  `height` int(6) NOT NULL default '0',
  `yaxis_label` varchar(100) default NULL,
  `xaxis_label` varchar(100) default NULL,
  `color` int(10) NOT NULL default '0',
  `transpose` varchar(5) default NULL,
  `stacked` varchar(5) default NULL,
  `chart_dimension` varchar(40) NOT NULL default 'TwoDimensional',
  PRIMARY KEY (`interface_chart_item_id`,`interface_id`,`content_id`,`part_id`)
  
) Type=InnoDB;



DROP TABLE IF EXISTS `interface_report_content`;

CREATE TABLE  `interface_report_content` (
  `interface_report_item_id` mediumint(9) NOT NULL auto_increment,
  `interface_id` varchar(100) NOT NULL,
  `content_id` varchar(100) NOT NULL default '',
  `part_id` varchar(100) NOT NULL default '',
  `rptdesign_file_name` varchar(100) default NULL,
   `viewer_type` varchar(100) default NULL,
   report_chooser varchar(100) default NULL,
   report_executer varchar(100) default NULL,
 
  PRIMARY KEY (`interface_report_item_id`,`interface_id`,`content_id`,`part_id`)
 ) Type=InnoDB;


DROP TABLE IF EXISTS report_parameter;

CREATE TABLE  report_parameter (
  `interface_report_parameter_id` mediumint(9) NOT NULL auto_increment,

  `interface_id` varchar(100) NOT NULL,
  `content_id` varchar(100) NOT NULL default '',
  `part_id` varchar(100) NOT NULL default '',
   parameter_name varchar(200) default NULL,
   parameter_value varchar(300) default NULL,
   parameter_value_type varchar(300) default NULL,
   value_source_item_name varchar(300) default NULL,
  PRIMARY KEY  (`interface_report_parameter_id`,interface_id,content_id,part_id,parameter_name)
 ) Type=InnoDB;


DROP TABLE IF EXISTS grid_column_snipet;
CREATE TABLE grid_column_snipet(
   interface_id  varchar(100),
   part_id   varchar(100)  ,
   column_name   varchar(100)  ,
   grid_snipet   varchar(200),
   
   PRIMARY KEY  (interface_id,part_id,column_name)
)Type=InnoDB;
create INDEX grid_column_snipet1 on grid_column_snipet(interface_id,part_id,column_name);


-- //////////////////////////////////// APPLICATION TEMPLATE AND THEMES///////////////////

drop table if exists application_template_master;

create table application_template_master(
application_template_id          mediumint not null auto_increment,
application_template_title             varchar(300),
templatecomment      varchar(300),
upload_on            date,
default_value        varchar(100),
applivation_xml_value   blob,
primary key(application_template_id)
)TYPE=InnoDB;
create INDEX  application_template_master1 on application_template_master(application_template_id);
 
drop table if exists application_template_asset;
 
create table application_template_asset(

application_template_id        int,
delivery_mode          varchar(300),
asset_type     varchar(300),
location      varchar(100),
sequence_no  int(10),
file_name   varchar(300),
asset_path  varchar(300),

primary key(sequence_no,application_template_id)
)TYPE=InnoDB;
create INDEX  application_template_asset1 on application_template_asset(sequence_no,application_template_id);
 

DROP TABLE IF EXISTS application_template_default;
CREATE TABLE application_template_default(
   application_template_id int,
   section_name  varchar(100),
   class_name   varchar(100)  ,
   attribute_name   varchar(100)  ,
   default_value   varchar(100),
   
   PRIMARY KEY  (application_template_id,section_name,class_name,attribute_name)
)Type=InnoDB;
create INDEX application_template_default1 on application_template_default(application_template_id,section_name,class_name,attribute_name);


DROP TABLE IF EXISTS themes;
CREATE TABLE themes(
   themes_id  varchar(200),
   default_value  varchar(100),
   xml_value  blob,
   PRIMARY KEY  (themes_id)
)Type=InnoDB;
create INDEX themes1 on themes(themes_id);

DROP TABLE IF EXISTS themes_definition;
CREATE TABLE themes_definition(
   themes_id  varchar(200),
   themes_element_id int(9) NOT NULL auto_increment,
   class_type  varchar(200),
   prop_type   varchar(200),
   css_classes   varchar(200),
   properties  blob,
   property_application   varchar(200),
   PRIMARY KEY  (themes_element_id)
)Type=InnoDB;
create INDEX themes_definition1 on themes_definition(themes_element_id);

DROP TABLE IF EXISTS themes_css;
CREATE TABLE themes_css(
   themes_css_id int(9) NOT NULL auto_increment,
   themes_id  varchar(200),
   css_value  varchar(300),
   PRIMARY KEY  (themes_css_id)
)Type=InnoDB;
create INDEX themes_css1 on themes_css(themes_css_id);

DROP TABLE IF EXISTS framework_asset;
CREATE TABLE framework_asset(
   asset_id   int(9) NOT NULL auto_increment,
   file_name  varchar(200),
   upload_on  datetime,
   asset_type varchar(300),
   value  blob,
   PRIMARY KEY  (asset_id)
)Type=InnoDB;
create INDEX framework_asset1 on framework_asset(asset_id);



/**************************Cache********************************/
Drop table if exists cache_definition;
create table cache_definition(
cache_id                    mediumint          NOT NULL auto_increment      ,
cache_name                  varchar(100)       NOT NULL default '' unique   ,
cache_type                  varchar(20)                                     ,
max_element                 int                NOT NULL                     ,
overflowtodisk              boolean            NOT NULL default TRUE        ,
timetoliveseconds           int                NOT NULL default 0           ,
timetoidleseconds           int                NOT NULL default 0           ,
diskpersistent              boolean            NOT NULL default FALSE       ,
eternal                     boolean            NOT NULL default FALSE       ,
diskexpirythreadintervalseconds  int           NOT NULL default 0           ,
memorystoreevictionpolicy   varchar(10)        NOT NULL default 'LFU'       ,
diskstorepath               varchar(250)                                    ,
default_cache               varchar(10)        NOT NULL default 'No'       ,
primary key(cache_id)
)TYPE=InnoDB;
