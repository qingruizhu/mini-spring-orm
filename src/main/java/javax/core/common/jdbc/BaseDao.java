package javax.core.common.jdbc;

import com.zqr.ddup.orm.framework.QueryRule;

import javax.core.common.Page;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2019-05-13 16:46
 */
public interface BaseDao<T,PK> {
    /**
     * 获取列表
     **/
    List<T> select(QueryRule queryRule) throws Exception;

    /**
     * 获取分页结果
     * @param queryRule 查询条件
     * @param pageNo    页码
     * @param pageSize  每页条数
     * @return
     * @throws Exception
     */
    Page<?> select(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

    /**
     * 根据sql查询列表
     * @param sql
     * @param args
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectBySql(String sql,Object... args) throws Exception;

    /**
     * 根据sql进行分页查询
     * @param sql
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    Page<Map<String,Object>> selectBySqlToPage(String sql,Object[] param,int pageNo,int pageSize) throws Exception;

    /**
     * 删除一条记录
     * @param entity 必须有一个字段不能为空，才能删除成功
     * @return
     * @throws Exception
     */
    boolean delete(T entity) throws Exception;

    /**
     * 批量删除
     * @param lst
     * @return  删除的行数
     * @throws Exception
     */
    int deleteAll(List<T> lst) throws Exception;

    /**
     * 插入一条记录并返回插入后的ID
     * @param entity
     * @return
     * @throws Exception
     */
    PK insertAndReturnId(T entity) throws Exception;

    /**
     * 插入一条记录id自增
     * @param entity
     * @return
     * @throws Exception
     */
    boolean insert(T entity) throws Exception;

    /**
     * 批量插入
     * @param lst
     * @return  插入的行数
     * @throws Exception
     */
    int insertAll(List<T> lst) throws Exception;

    /**
     * 修改
     * @param entity    都为空不执行
     * @return
     * @throws Exception
     */
    boolean update(T entity) throws Exception;
}
