package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.DistrictMapper;
import cn.tedu.store.service.DistrictService;

/**
 * 处理省市区数据的业务层实现类
 */
@Service
public class DistrictServiceImpl implements DistrictService {
	
	@Autowired
	private DistrictMapper districtMapper;
	
	@Override
	public String getNameByCode(String code) {
		return districtMapper.findNameByCode(code);
	}
	
	@Override
	public List<District> getByParent(String parent) {
		List<District> result = districtMapper.findByParent(parent);
		for (District district : result) {
			district.setId(null);
			district.setParent(null);
		}
		return result;
	}
	
}
