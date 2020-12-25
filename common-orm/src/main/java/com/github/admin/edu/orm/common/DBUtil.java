package com.github.admin.edu.orm.common;

import com.github.admin.edu.assembly.page.entity.Pager;
import com.github.admin.edu.assembly.string.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-11
 * jdbc 操作
 */
@Component
public class DBUtil {

    private static final Logger log = LoggerFactory.getLogger(DBUtil.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.data-type}")
    private String dataType;

    /**
     * 根据条件查询全部对象
     *
     * @param pager
     * @param sql
     * @param beanClass
     * @param params
     * @param <T>
     * @return
     */
    @Transactional(readOnly = true)
    public <T> List<T> getAllPagerBeanList(Pager pager, String sql, Class<T> beanClass, Object... params) {
        if (null != pager) {
            sql = getSql(sql, pager);
        }
        params = deleteNull(params);
        List<T> results = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(beanClass), params);
        return results;
    }

    @Transactional(readOnly = true)
    public <T> List<T> getAllSqlServerPagerBeanList(Pager pager, String sql, Class<T> beanClass, Object... params) {
        if (null != pager) {
            sql = convert2SqlServerPageSQL(pager, sql);
        }
        params = deleteNull(params);
        List<T> results = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(beanClass), params);
        return results;
    }

    @Transactional(readOnly = true)
    public <T> List<T> getAllMySqlPagerBeanList(Pager pager, String sql, Class<T> beanClass, Object... params) {
        if (null != pager) {
            sql = convert2MysqlPagedSQL(pager, sql);
        }
        params = deleteNull(params);
        List<T> results = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(beanClass), params);
        return results;
    }

    @Transactional(readOnly = true)
    public <T> List<T> getAllMySqlBeanList(String sql, Class<T> beanClass, Object... params) {
        params = deleteNull(params);
        List<T> results = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(beanClass), params);
        return results;
    }

    @Transactional(readOnly = true)
    public <T> T getMySqlEntity(String sql, Class<T> beanClass, Object... params) {
        List<T> results = getAllMySqlBeanList(sql, beanClass, params);
        if (null != results && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public <T> List<T> getAllBeanList(String sql, Class<T> beanClass) {
        List<T> results = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(beanClass));
        return results;
    }

    @Transactional(readOnly = true)
    public <T> List<T> getAllBeanList(String sql, Class<T> beanClass, Object... params) {
        params = deleteNull(params);
        List<T> results = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(beanClass), params);
        return results;
    }

    @Transactional
    public <T> T queryFieldValue(String sql, Class<T> beanClass) {

        List<T> results = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(beanClass));
        if (null != results && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    /**
     * 查询单个个对象
     *
     * @param sql
     * @param beanClass
     * @param params
     * @param <T>
     * @return
     */
    @Transactional(readOnly = true)
    public <T> T queryFieldValue(String sql, Class<T> beanClass, Object... params) {
        params = deleteNull(params);
        List<T> results = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(beanClass), params);
        if (null != results && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }


    /**
     * 查询单个字段
     *
     * @param sql
     * @return
     */
    @Transactional(readOnly = true)
    public Object queryForOne(String sql, Object... params) {
        params = deleteNull(params);
        Object object = jdbcTemplate.queryForObject(sql, Object.class, params);
        return object;
    }


    @Transactional
    public int create(String sql) {
        if (StringUtils.isNotBlank(sql)) {
            return jdbcTemplate.update(sql);
        }
        return 0;
    }

    @Transactional
    public void create(String sql, Object... params) {
        params = deleteNull(params);
        if (null != params && params.length > 0) {
            int k = params.length;
            Object[] objects = params;
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql);
                    for (int i = 0; i < k; i++) {
                        ps.setObject(i + 1, objects[i]);
                    }
                    return ps;
                }
            });
        }
    }

    @Transactional
    public void update(String sql) {
        jdbcTemplate.update(sql);
    }


    @Transactional
    public void update(String sql, Object... objects) {
        jdbcTemplate.update(sql, objects);
    }

    @Transactional
    public int createNoId(String sql, Object... params) {
        params = deleteNull(params);
        if (null != params && params.length > 0) {
            int k = params.length;
            Object[] objects = params;
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    for (int i = 0; i < k; i++) {
                        ps.setObject(i + 1, objects[i]);
                    }
                    return ps;
                }
            }, holder);
            return holder.getKey().intValue();
        }
        return 0;
    }

    /**
     * 计算总页数
     *
     * @param sql
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public int count(String sql, Object... params) {
        params = deleteNull(params);
        if (StringUtils.isBlank(sql)) {
            throw new RuntimeException("期望是select count(*) from ...查询语句，实际传入了空字符串！");
        }

        if (sql.trim().toUpperCase().startsWith("FROM")) {
            sql = "SELECT COUNT(*) " + sql;
        }
        if (!sql.toUpperCase().contains("COUNT(")) {
            throw new RuntimeException(sql + " 不是有效的select count(*) from ...查询语句！");
        }
        int rowsCount = 0;
        Object obj = jdbcTemplate.queryForObject(sql, new RowMapper<Object>() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getObject(1);
            }
        }, params);
        rowsCount = Integer.parseInt(obj.toString());
        return rowsCount;
    }

    @Transactional(readOnly = true)
    public Object getOneObject(String sql, Object... params) {
        params = deleteNull(params);
        if (StringUtils.isBlank(sql)) {
            throw new RuntimeException("---SQL 不能为空");
        }
        Object obj = jdbcTemplate.queryForObject(sql, new RowMapper<Object>() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getObject(1);
            }
        }, params);
        return obj;
    }

    /**
     * 默认 oracle
     *
     * @param sql
     * @param pager
     * @return
     */
    private String getSql(String sql, Pager pager) {
        if ((dataType.toLowerCase()).equals("sqlserver")) {
            return convert2SqlServerPageSQL(pager, sql);
        } else if ((dataType.toLowerCase()).equals("mysql")) {
            return convert2MysqlPagedSQL(pager, sql);
        } else {
            return convert2OraclePagedSQL(pager, sql);
        }

    }


    /**
     * MySql分页查询 sql拼装
     *
     * @param pager
     * @param sql
     * @return
     */
    private String convert2MysqlPagedSQL(Pager pager, String sql) {
        int startInt = (pager.getCurrent() - 1) * pager.getRows();
        sql = sql + " limit " + startInt + "," + pager.getRows();
        return sql;
    }

    /**
     * Oracle分页查询 sql拼装
     *
     * @param pager
     * @param sql
     * @return
     */
    private String convert2OraclePagedSQL(Pager pager, String sql) {
        int startInt = (pager.getCurrent() - 1) * pager.getRows();
        int pageSize = pager.getCurrent() * pager.getRows();
        sql = "SELECT * FROM (SELECT ROW_.*,ROWNUM RN FROM ( " + sql + ") ROW_ ) WHERE RN > " + startInt + " AND RN <= " + pageSize;
        return sql;
    }

    /**
     * Sql server分页查询
     *
     * @param pager
     * @param sql
     * @return
     */
    private String convert2SqlServerPageSQL(Pager pager, String sql) {
        sql = sql.substring(6, sql.length());
        int startInt = (pager.getCurrent() - 1) * pager.getRows();
        int pageSize = pager.getCurrent() * pager.getRows();
        sql = "select * from (select row_number() over (order by tempcolumn) temprownumber,* " +
                " from (select top (" + pageSize + ") tempcolumn=0," + sql + ")pager_t)pager_tt where temprownumber>" + startInt;
        return sql;
    }

    /**
     * 去掉参数中null字段
     *
     * @param params
     * @return
     */
    private Object[] deleteNull(Object[] params) {
        ArrayList<Object> args = new ArrayList<Object>();
        for (Object o : params) {
            if (o != null) {
                args.add(o);//去除参数中的 null
            }
        }
        return args.toArray();
    }

    public List<byte[]> getBlob(final String sql, String columnLabel, Object[] args) {
        List<byte[]> result = new ArrayList();
        this.jdbcTemplate.query(sql, args, new ResultSetExtractor<List<byte[]>>() {

            @Override
            public List<byte[]> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                while (resultSet.next()) {
                    Blob blob = resultSet.getBlob(columnLabel);
                    int len = (int) blob.length();
                    result.add(blob.getBytes(1, len));
                }
                return result;
            }
        });
        return result;
    }

    public Blob getBlobColumn(final String sql, String columnLabel, Object[] args) {

        return this.jdbcTemplate.query(sql, args, new ResultSetExtractor<Blob>() {

            @Override
            public Blob extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                Blob result = null;
                while (resultSet.next()) {
                    result = resultSet.getBlob(columnLabel);
                }
                return result;
            }
        });

    }

    public String convertBlobToBase64String(Blob blob) {
        String result = "";
        if (null != blob) {
            try {
                InputStream msgContent = blob.getBinaryStream();
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[100];
                int n = 0;
                while (-1 != (n = msgContent.read(buffer))) {
                    output.write(buffer, 0, n);
                }
                result = new BASE64Encoder().encode(output.toByteArray());
                output.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        } else {
            return null;
        }
    }

}
