<?php

class MeController extends Zend_Controller_Action
{

    public function init()
    {
		$this->_helper->viewRenderer->setNoRender(TRUE);
    }

    public function getEventsAction()
    {
   		 	$user = $this->getUser();
			$eventTable = new Application_Model_DbTable_Events();
			$data = array();
			$eventObj = $eventTable->find($user->bsn)->current();
			if($eventObj != null){
			foreach ($eventObj as $event)
			{
				$dataRow = array();
				$dataRow['short_desc'] = $event->short_desc;
				$dataRow['desc'] = $event->desc;
				$dataRow['date'] = $event->date;
				array_push($data, $dataRow);	
			}
			$data = array('status' => 'SUCCES', 'message'=>$data);
			}else
			{
			$data = array('status' => 'SUCCES', 'message'=>'no data');
			}
			echo $this->_helper->json($data);
			exit();
    	
    }

    public function getDataAction()
    {
		$user = $this->getUser();
			$eventTable = new Application_Model_DbTable_Kvk();
			$data = array();
			$kvkObj = $eventTable->find($user->bsn)->current();
			if($kvkObj != null){
			foreach ($kvkObj as $kvk)
			{
				$dataRow = array();
				$dataRow['kvk'] = $kvk->kvk;
				$dataRow['bedrijfsnaam'] = $kvk->bedrijfsnaam;
				$dataRow['adres'] = $kvk->adres;
				$dataRow['postcode'] = $kvk->postcode;
				$dataRow['plaats'] = $kvk->plaats;
				$dataRow['type'] = $kvk->type;
				$dataRow['status'] = $kvk->status;
				$dataRow['website'] = $kvk->website;
				$dataRow['rechtsvorm'] = $kvk->rechtsvorm;
 				array_push($data, $dataRow);
			}
			$data = array('status'=>'SUCCES', 'message'=>$data);
			}
			else
			{
				$data = array('status'=>'SUCCES', 'message'=>'no data');
			}
			echo $this->_helper->json($data);	
			exit();
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





