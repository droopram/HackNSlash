<?php
class MeController extends Zend_Controller_Action {
	public function init() {
		$this->_helper->viewRenderer->setNoRender ( TRUE );
	}
	public function getEventsAction() {
		$user = $this->getUser ();
		$eventTable = new Application_Model_DbTable_Events ();
		$data = array ();
		$eventObj = $eventTable->find ( $user->bsn )->current ();
		if ($eventObj != null) {
			foreach ( $eventObj as $event ) {
				$dataRow = array ();
				$dataRow ['short_desc'] = $event->short_desc;
				$dataRow ['desc'] = $event->desc;
				$dataRow ['date'] = $event->date;
				array_push ( $data, $dataRow );
			}
			$data = array (
					'status' => 'SUCCES',
					'message' => $data 
			);
		} else {
			$data = array (
					'status' => 'SUCCES',
					'message' => 'no data' 
			);
		}
		echo $this->_helper->json ( $data );
		exit ();
	}
	
	public function getDataAction() {
		/**
		 * Initiating the data arrays
		 */
		$passportData = array ();
		$letterData = array ();
		$userData = array ();
		$kvkData = array ();
		
		/* Getting the userobject */
		$user = $this->getUser ();
		/*
		 * initializing the required tables
		 */
		$kvkTable = new Application_Model_DbTable_Kvk ();
		$letterTable = new Application_Model_DbTable_Brieven ();
		$passportTable = new Application_Model_DbTable_Passport ();
		$gbaTable = new Application_Model_DbTable_Gba ();
		
		/*
		 * Getting the local user data
		 */
		$userObj = $gbaTable->find ( $user->bsn )->current ();
		
		if ($userObj != null) {
			/*
			 * If user object doesn't exist get the user object from "gemeente burgerschap administratie"
			 */
		}
		
		$letterObj = $letterTable->find ( $user->bsn )->current ();
		if ($letterObj != null) {
			foreach ( $letterObj as $letter ) {
				$dataRow = array();
				$dataRow['onderwerp']=$letter->onderwerp;
				$dataRow['verzenddatum']=$letter->verzenddatum;
				$dataRow['adres']=$letter->adres;
				$dataRow['postcode']=$letter->postcode;
				$dataRow['plaats']=$letter->Plaats;
				$dataRow['prioriteit']=$letter->prioriteit;				
				array_push($letterData, $dataRow);
			}
		}
		
		$passportObj = $passportTable->find($user->bsn)->current();
		if($passportObj != null)
		{
			foreach ($passportObj as $passport)
			{
				$dataRow = array();
				$dataRow['documentnr'] = $passport->documentnr;
				$dataRow['vervaldatum'] = $passport->vervaldatum;
				$dataRow['kvknummer'] = $passport->kvknummer;
				$dataRow['ontvangen'] = $passport->ontvangen;
				$dataRow['beschrijving'] = $passport->beschrijving;
				array_push($passportData, $dataRow);
			}
		}
		
		
		
		
		
		
		$kvkObj = $kvkTable->find ( $user->bsn )->current ();
		if ($kvkObj != null) {
			foreach ( $kvkObj as $kvk ) {
				$dataRow = array ();
				$dataRow ['kvk'] = $kvk->kvk;
				$dataRow ['bedrijfsnaam'] = $kvk->bedrijfsnaam;
				$dataRow ['adres'] = $kvk->adres;
				$dataRow ['postcode'] = $kvk->postcode;
				$dataRow ['plaats'] = $kvk->plaats;
				$dataRow ['type'] = $kvk->type;
				$dataRow ['status'] = $kvk->status;
				$dataRow ['website'] = $kvk->website;
				$dataRow ['rechtsvorm'] = $kvk->rechtsvorm;
				array_push ( $kvkData, $dataRow );
			}
		}
		
		array_push ( $data, $kvkData );
		array_push ( $data, $userData );
		array_push ( $data, $letterData );
		array_push ( $data, $passportData );
		
		$data = array (
				'status' => 'SUCCES',
				'message' => $data, 
		);
		
		echo $this->_helper->json ( $data );
		exit ();
	}
	
	/*
	 * Params: POST skey, The secret key Return: user object on success, exit on failure.
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
}





