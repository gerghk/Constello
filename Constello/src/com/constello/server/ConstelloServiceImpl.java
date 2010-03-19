package com.constello.server;

import com.constello.client.ConstelloService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ConstelloServiceImpl extends RemoteServiceServlet implements
		ConstelloService {

	public int[] sendMove(int[] move) {
		
		int[] counter = new int[3];
		counter[0] = 5;
		counter[1] = 6;
		counter[2] = 7;
		return counter;
	}

}
