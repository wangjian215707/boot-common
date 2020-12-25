-- Create table
create table T_SYS_JWT
(
  id      VARCHAR2(100) not null,
  name    VARCHAR2(200),
  pub_key VARCHAR2(200),
  state   NUMBER,
  age     NUMBER
)
tablespace USERS
pctfree 10
initrans 1
maxtrans 255;
-- Add comments to the columns
comment on column T_SYS_JWT.id
is '服务编号';
comment on column T_SYS_JWT.name
is '服务名称';
comment on column T_SYS_JWT.pub_keyTSysUserServiceImpl
is '服务公钥';
comment on column T_SYS_JWT.state
is '状态';
comment on column T_SYS_JWT.age
is '时效';
-- Create/Recreate primary, unique and foreign key constraints
alter table T_SYS_JWT
  add constraint P_SYS_P_KEY_ID primary key (ID)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
