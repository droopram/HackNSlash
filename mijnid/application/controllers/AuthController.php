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
		$raw = $this->getRequest()->getRawBody();
		$json = Zend_Json::decode($raw);
		$pin = $json['pin'];
		$device_id = $json['device_id'];
		$registrant_id = $json['registrant_id'];
		if(!isset($pin) || !isset($device_id)){
			$this->getResponse()->setHttpResponseCode(400);
			$data = array('status'=>'FAILED','message'=>'NO_DATA_PROVIDED');
			echo $this->_helper->json($data);
			exit();
		}
		$authTable = new Application_Model_DbTable_Auth();
		$user = $authTable->fetchRow($authTable->select()->where('pin=?',$pin)->where('device_id=?',$device_id));
		
		//Row not found
		if($user==null){
			$this->getResponse()->setHttpResponseCode(400);
			$data = array('status'=>'FAILED','message'=>'INVALID_LOGIN');
			echo $this->_helper->json($data);
			exit();
		}
		
		//Generate SKEY now.
		$skey = $this->generateKey();
		//Save the users secret key for this session.
		$user->secret_key = $skey;
		$user->registrant_id = $registrant_id;
		$user->save();
		
		//Let the api consumer know it worked.		
		$data = array('status'=>'SUCCESS','skey'=>$skey,'bsn'=>$user->bsn);
		echo $this->_helper->json($data);
    }

    public function logoutAction()
    {
		//Get user based on SKEY
		$user = $this->getUser();
		$user->secret_key = NULL;
		$user->save();
		
        //Delete the SKEY from the DB
		$data = array('status'=>'SUCCESS','message'=>'Successfully logged out.');
		echo $this->_helper->json($data);
    }
	
	/*
	*	Params: POST skey, The secret key
	* 	Return: user object on success, exit on failure.
	*/	
	private function getUser(){
		$raw = $this->getRequest()->getRawBody();
		$json = Zend_Json::decode($raw);
		$skey = $json['skey'];
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
	
	/*
	*	Params: Generate a key with a length of $length
	*	Return: SHA1 hash of a random generated key.
	*/
	private function generateKey($length=50) {
		$random = '';
		for ($i = 0; $i < $length; $i++) {
			$random .= chr(mt_rand(33, 126));
		}
		return sha1($random);
	}
}