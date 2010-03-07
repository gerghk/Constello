package com.constello.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("server")
public interface ConstelloService extends RemoteService {
	
	int[] sendMove(int[] move);
}
