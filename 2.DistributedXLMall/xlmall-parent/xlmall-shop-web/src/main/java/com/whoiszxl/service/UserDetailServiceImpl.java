package com.whoiszxl.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.whoiszxl.pojo.TbSeller;
import com.whoiszxl.sellergoods.service.SellerService;

/**
 * 商家用户认证类
 * @author Administrator
 *
 */
public class UserDetailServiceImpl implements UserDetailsService{
	
	private SellerService sellerService;
	
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));
		
		TbSeller tbSeller = sellerService.findOne(username);
		if(tbSeller != null && tbSeller.getStatus().equals("1")) {
			return new User(username, tbSeller.getPassword(), grantedAuthorities);
		}else {
			return null;
		}
		
	}

}
