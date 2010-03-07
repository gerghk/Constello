package com.constello.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ConstelloServiceAsync {

	void sendMove(int[] move, AsyncCallback<int[]> callback);
}
