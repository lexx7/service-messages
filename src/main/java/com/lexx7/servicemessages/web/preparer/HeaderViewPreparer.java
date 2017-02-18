package com.lexx7.servicemessages.web.preparer;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.lexx7.servicemessages.business.service.UserService;
import com.lexx7.servicemessages.web.dto.HeaderData;

@Component
public class HeaderViewPreparer implements ViewPreparer {

	/**
	 * HEADER_DATA_ATTRIBUTE name
	 * 
	 * Name and Class name is same, but first symbol in lowcase. "headerData"
	 */
	public static final String HEADER_DATA_ATTRIBUTE = Character.toLowerCase(HeaderData.class.getSimpleName().charAt(0)) + HeaderData.class.getSimpleName().substring(1);
	
	@Autowired
	private UserService userService;

	@Override
	public void execute(Request request, AttributeContext attributeContext) {
		HeaderData headerData = new HeaderData();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && authentication.getName() != "anonymousUser") {
			headerData.setCurrentUserId(userService.getUserByLogin(authentication.getName()).getId().toString());
			
		}
		attributeContext.putAttribute(HEADER_DATA_ATTRIBUTE, new Attribute(headerData));
	}

}
