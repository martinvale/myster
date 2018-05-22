alter table choice drop foreign key fk_item_option;
alter table choice drop key fk_item_option;
alter table choice change column item_option_id survey_item_id bigint not null;
alter table choice add constraint fk_choice_survey_item foreign key (survey_item_id) references survey_item(id);
