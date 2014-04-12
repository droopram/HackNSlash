<?php

class AuthController extends Zend_Controller_Action
{

    public function init()
    {
		$this->_helper->viewRenderer->setNoRender(TRUE);
    }
	
    public function loginAction()
    {
		//Auth User based on PIN & device ID
		$pin = $this->getRequest()->getPost('pin');
		$pin = $this->getRequest()->getPost('device_id');
		//Generate SKEY
		//Save SKEY
		//DB req.
		//Give back the Skey
		
		$data = array('status'=>'SUCCESS','skey'=>'adsf');
		echo $this->_helper->json($data);
    }

    public function logoutAction()
    {
		//Get user based on SKEY
        //Delete the SKEY from the DB
		$data = array('status'=>'SUCCESS','message'=>'Successfully logged out.');
		echo $this->_helper->json($data);
    }
	
	/*
	*	Params: POST skey, The secret key
	* 	Return: user object on success, exit on failure.
	*/	
	private function getUser(){
		$skey = $this->getRequest()->getPost('skey');
		//$user = $usertable->fetchRow($usertable->select()->where('skey=?',$skey));
		if($user!=null)
			return $user;
		else {
			$data = array('status'=>'FAILED','message'=>'INVALID_REQUEST');
			echo $this->_helper->json($data);
			exit();
		}
	}
}





