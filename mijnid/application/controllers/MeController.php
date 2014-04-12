<?php

class MeController extends Zend_Controller_Action
{

    public function init()
    {
		$this->_helper->viewRenderer->setNoRender(TRUE);
    }

    public function getEventsAction()
    {
		//get user
		//give data -> Timeline met timestamps
    }

    public function getDataAction()
    {
		//get user
		//give data -> KVK
    }
	
	/*
	*	Params: POST skey, The secret key
	* 	Return: user object on success, exit on failure.
	*/	
	private function getUser(){
		$skey = $this->getRequest()->getPost('skey');
		$authTable = new Application_Model_DbTable_Auth();
		$user = $authTable->fetchRow($authTable->select()->where('secret_key=?',$skey));
		if($user!=null)
			return $user;
		else {
			$this->getResponse()->setHttpResponseCode(400);
			$data = array('status'=>'FAILED','message'=>'INVALID_REQUEST');
			echo $this->_helper->json($data);
			exit();
		}
	}
}





