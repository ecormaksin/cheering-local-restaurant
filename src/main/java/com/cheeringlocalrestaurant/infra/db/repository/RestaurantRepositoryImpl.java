package com.cheeringlocalrestaurant.infra.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cheeringlocalrestaurant.domain.model.restaurant.Restaurant;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.UserRole;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.infra.db.jpa.EntityUtil;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.Resto;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.RestoAccount;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.RestoHistory;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.RestoName;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.RestoAccountRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.RestoHistoryRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.RestoNameRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.RestroRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.UserRepository;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

	@Autowired
	private RestroRepository restroRepository;
	@Autowired
	private RestoHistoryRepository restoHistoryRepository;
	@Autowired
	private RestoNameRepository restoNameRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RestoAccountRepository restoAccountRepository;
	
	@Override
	public RestaurantAccount findByMailAddress(String mailAddress) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Restaurant findById(RestaurantId restaurantId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public RestaurantAccount findAccountById(RestaurantId restaurantId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	@Transactional
	public RestaurantId save(RestaurantTempRegister tempRegister, String remoteIpAddress) {

		// 飲食店
		Resto resto = new Resto();
		EntityUtil.setCommonColumn(resto, remoteIpAddress);
		resto = restroRepository.save(resto);
		Long restoId = resto.getRestaurantId();
		
		// 飲食店履歴
		RestoHistory restoHistory = new RestoHistory();
		restoHistory.setRestaurantId(restoId);
		EntityUtil.setCommonColumn(restoHistory, remoteIpAddress);
		restoHistory = restoHistoryRepository.save(restoHistory);
		Long restoRevId = restoHistory.getRestaurantHistoryId();
		
		// 飲食店名
		RestoName restoName = new RestoName();
		restoName.setRestaurantHistoryId(restoRevId);
		restoName.setRestaurantName(tempRegister.getName().getValue());
		restoNameRepository.save(restoName);

		// ユーザー
		com.cheeringlocalrestaurant.infra.db.jpa.entity.User user = new com.cheeringlocalrestaurant.infra.db.jpa.entity.User();
		user.setMailAddress(tempRegister.getMailAddress().getValue());
		user.setUserRole(UserRole.RESTAURANT_ADMIN.getValue());
		EntityUtil.setCommonColumn(user, remoteIpAddress);
		user = userRepository.save(user);
		Long userId = user.getUserId();
		
		// 飲食店アカウント
		RestoAccount restoAccount = new RestoAccount();
		restoAccount.setRestaurantId(restoId);
		restoAccount.setUserId(userId);
		EntityUtil.setCommonColumn(restoAccount, remoteIpAddress);
		restoAccountRepository.save(restoAccount);
		
		return new RestaurantId(restoId);
	}

}
