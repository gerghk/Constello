package com.constello.server;

import com.constello.client.ConstelloService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ConstelloServiceImpl extends RemoteServiceServlet implements
		ConstelloService {

	@Override
	public int[] sendMove(int[] move) {
		
		int[] counter = new int[3];
		counter[0] = 1;
		counter[1] = 2;
		counter[2] = 0;
		return counter;
	}

}
