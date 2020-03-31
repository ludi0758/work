package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.District;

/*
 * 处理省市区数据的持久层接口
 */
public interface DistrictMapper {
	
	/**
	 * 根据省/市/区的行政代号查询名称
	 * @param code 省/市/区的行政代号
	 * @return 省/市/区的名称，如果没有匹配的数据，则返回null
	 */
	String findNameByCode(String code);
	
	/**
	 * 获取全国所有省你，或获取某省所有市，或获取某市所有区的列表
	 * @param parent 伏击但问的行政代号，如果获取谋士所有区的列表，该参数应该是市的行政代号，如果获取全国所有省的列表，该参数值使用"86"
	 * @return 全国所有省，或某省所有市，或某市所有区的列表
	 */
	List<District> findByParent(String parent);

}
