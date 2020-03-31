package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.mapper.DistrictMapper;
import cn.tedu.store.service.AddressService;
import cn.tedu.store.service.DistrictService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.AddressSizeLimitException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

/*
 * 处理收货地址数据的业务层实现类
 */
@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private DistrictMapper districtMapper;

	@Value("${project.address-max-size}")
	private int maxSize;

	@Override
	public void addnew(Integer uid, String username, Address address) {
		//基于参数uid调用addressMapper.countByUid()统计当前用户的收货地址数据的数量
		Integer count = addressMapper.countByUid(uid);
		//判断统计结果是否达到最大值（3）
		if(count>=maxSize){
			//是：抛出AddressSizeLimitException
			throw new AddressSizeLimitException("增加收货地址失败！您的收货地址的数量已经到达上限");
		}

		//创建时间对象
		Date now = new Date();
		//基于统计结果判断得到是否默认的值
		Integer isDefault = count == 0 ? 1 : 0 ;
		//补全参数address中的属性：uid > 参数uid
		address.setUid(uid);
		//补全参数address中的属性： is_default > 以上判断结果
		address.setIsDefault(isDefault);
		//补全参数address中的属性；日志 > 参数username，新创建对象
		address.setCreatedUser(username);
		address.setCreatedTime(now);
		address.setModifiedUser(username);
		address.setModifiedTime(now);
		//补全参数address中的属性；得到省市区的名字
		address.setProvinceName(districtMapper.findNameByCode(address.getProvinceCode()));
		address.setCityName(districtMapper.findNameByCode(address.getCityCode()));
		address.setAreaName(districtMapper.findNameByCode(address.getAreaCode()));
		//执行插入收货地址数据
		insert(address);
	}

	@Override
	public Address getByAid(Integer aid) {
		Address address = findByAid(aid);
		if(address == null){
			throw new AddressNotFoundException("获取收货地址失败！尝试访问的数据不存在！");
		}

		// -- 将不需要响应到客户端的属性设置为null：uid, province_code, city_code, area_code, is_default, 4个日志属性
		address.setProvinceCode(null);
		address.setCityCode(null);
		address.setAreaCode(null);
		address.setIsDefault(null);
		address.setCreatedUser(null);
		address.setCreatedTime(null);
		address.setModifiedUser(null);
		address.setModifiedTime(null);
		// 返回收货地址列表
		return address;// 返回收货地址列表
	}

	@Override
	public List<Address> getByUid(Integer uid) {
		// 调用持久层对象执行查询，得到收货地址列表
		List<Address> addresses = addressMapper.findByUid(uid);
		// 遍历查询到的收货地址列表
		for (Address address : addresses) {
			// -- 将不需要响应到客户端的属性设置为null：uid, province_code, city_code, area_code, is_default, 4个日志属性
			address.setUid(null);
			address.setProvinceCode(null);
			address.setCityCode(null);
			address.setAreaCode(null);
			address.setIsDefault(null);
			address.setCreatedUser(null);
			address.setCreatedTime(null);
			address.setModifiedUser(null);
			address.setModifiedTime(null);
		}
		// 返回收货地址列表
		return addresses;
	}

	@Override
	@Transactional
	public void setDefault(Integer aid, Integer uid, String username) {
		//基于参数aid调用addressMapper,findByAid()执行查询
		Address address = addressMapper.findByAid(aid);
		//判断查询结果是否为null
		if(address == null){
			//是：抛出AddressNotFoundException
			throw new AddressNotFoundException("设置默认收货地址失败！尝试访问的数据不存在！");
		}

		//判断数据归属是否正确，即查询结果中的uid与参数uid（控制器从Session中取出的uid）是否不一致，对比是，需要使用equals（）方法，不要使用==或！=运算符
		if(!address.getUid().equals(uid)){
			//是：抛出AccessDenicedException
			throw new AccessDeniedException("设置默认收货地址失败！非法访问已经被拒绝！");
		}

		//基于参数uid调用addressMapper,updateNonDefaultByUid()将该用户的所有收货地址设为“非默认”，并获取返回值
		Integer rows = addressMapper.updateNonDefaultByUid(uid);
		//判断返回结果是否小于1
		if(rows<1){
			//是：抛出UpdateException
			throw new UpdateException("[1] 设置默认收货地址失败！更新收货地址数据时出现未知错误，请联系系统管理员！");
		}

		//调用addressMapper.updateDeffaultByAid()将指定的收货地址设置为“默认”，并获取返回值
		Date modifiedTime = new Date();

		//将指定的收货地址设置为“默认”
		updateDefaultByAid(aid,username,new Date());
	}

	@Override
	@Transactional
	public void delete(Integer aid, Integer uid, String username) {
		//基于参数aid调用findByAid()执行查询
		Address result = findByAid(aid);
		//判断查询结果是否为null
		if(result == null){
			//是:抛出AddressNotFoundException
			throw new AddressNotFoundException("删除收货地址失败！尝试访问的数据不存在");
		}

		// 判断数据归属是否正确，即查询结果中的uid与参数uid(控制器层从Session中取出的uid)是否不一致，
		// 对比时，需要使用equals()方法，不要使用 == 或 != 运算符
		if(!uid.equals(result.getUid())){
			//是：抛出AccessDeniedException
			throw new AccessDeniedException("删除收货地址失败！非法访问已经被拒绝");
		}

		//调用deleteByAid()执行删除
		deleteByAid(aid);

		//判断查询结果中的isDefault是否为0（刚才删除的不是默认收货地址）
		if(result.getIsDefault() != 0){
			return;
		}

		//调用countByUid()统计目前该用户的收货地址数量
		Integer count = addressMapper.countByUid(uid);

		//判断统计结果是否为0（该用户刚才确实删除了默认收货地址，但是，是最后一条收货地址）
		if(count == 0){
			return;
		}

		//调用findLastModified()查询该用户最近修改的收货地址
		Address LastModified = addressMapper.findLastModified(uid);
		//取出此次查询结果中的aid
		Integer lastModifiedAid = LastModified.getAid();

		//将指定的收货地址设置为“默认”
		updateDefaultByAid(aid,username,new Date());
	}

	/**
	 * 插入收货地址数据
	 * @param address 收货地址数据
	 */
	private void insert(Address address){
		Integer rows = addressMapper.insert(address);
		if(rows != 1){
			throw new InsertException("插入收货地址失败！插入收货地址数据时出现未知错误，请联系系统管理员");
		}
	}

	/**
	 * 删除收货地址
	 * @param aid 收货地址的id
	 */
	private void deleteByAid(Integer aid){
		Integer rows = addressMapper.deleteByAid(aid);
		if(rows != 1){
			throw new DeleteException("删除收货地址失败！删除收货地址数据时出现未知错误，请联系系统管理员");
		}
	}

	/**
	 * 将某收货地址设置为默认
	 * @param aid 收货地址的id
	 * @param modifiedUser 最后修改人
	 * @param modifiedTime 最后修改时间
	 */
	private void updateDefaultByAid(Integer aid,String modifiedUser,Date modifiedTime){
		Integer rows = addressMapper.updateDefaultByAid(aid, modifiedUser,modifiedTime);
		if(rows != 1){
			throw new UpdateException("设置默认收货地址失败！更新收货地址数据时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 将某用户的所有收货地址设置为非默认
	 * @param uid 用户id
	 */
	private void updateNonDefaultByUid(Integer uid){
		Integer rows = addressMapper.updateNonDefaultByUid(uid);
		if(rows < 1){
			throw new UpdateException("设置默认收货地址失败！更新收货地址数据时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 统计某个用户的收货地址的数量
	 * @param uid 用户的id
	 * @return 该用户的收货地址的数量
	 */
	private Integer countByUid(Integer uid){
		return addressMapper.countByUid(uid);
	}

	/**
	 * 根据收货地址id查询收货地址详情
	 * @param aid 收货地址id
	 * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
	 */
	private Address findByAid(Integer aid) {
		return addressMapper.findByAid(aid);
	}

	/**
	 * 查询某用户最近修改的收货地址
	 * @param uid 用户的id
	 * @return 该用户最近修改的收货地址
	 */
	private Address findLastModified(Integer uid) {
		return addressMapper.findLastModified(uid);
	}

	/**
	 * 查询某用户的收货地址列表
	 * 
	 * @param uid 用户的id
	 * @return 该用户的收货地址列表
	 */
	private List<Address> findByUid(Integer uid) {
		return addressMapper.findByUid(uid);
	}


}
