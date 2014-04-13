<?php
class MeController extends Zend_Controller_Action {
	public function init() {
		$this->_helper->viewRenderer->setNoRender ( TRUE );
	}
	public function getEventsAction() {
		$user = $this->getUser ();
		$eventTable = new Application_Model_DbTable_Events ();
		$data = array ();
		$eventObj = $eventTable->fetchAll($eventTable->select()->where('bsn = ?', $user->bsn)->where('timeline = ?', 1));
		if ($eventObj != null) {
			foreach ( $eventObj as $event ) {
				$dataRow = array ();
				$dataRow ['short_desc'] = $event->short_desc;
				$dataRow ['desc'] = $event->desc;
				$dataRow ['date'] = $event->date;
				$dataRow ['panic-level'] = $event->panic_level;
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
		$data = array();
		/* Getting the userobject */
		$user = $this->getUser ();
		/*
		 * initializing the required tables
		 */
		$kvkTable = new Application_Model_DbTable_Kvk ();
		$letterTable = new Application_Model_DbTable_Brieven ();
		$passportTable = new Application_Model_DbTable_Passport ();
		$gbaTable = new Application_Model_DbTable_Gba ();
		$overheidTable = new Application_Model_DbTable_OverheidGba();
		$eventTable =new Application_Model_DbTable_Events();
		/*
		 * Getting the local user data
		 */
		$userObj = $gbaTable->find($user->bsn)->current();
		
		if ($userObj == null) {
			/*
			 * If user object doesn't exist get the user object from "gemeente burgerschap administratie"
			 */
			
			$userObj = $overheidTable->find($user->bsn)->current();
			
			$userRow = $gbaTable->createRow();
			$userRow->bsn = $userObj->bsn;
			$userRow->voornaam = $userObj->voornaam;
			$userRow->achternaam = $userObj->achternaam;
			$userRow->geslacht = $userObj->geslacht;
			$userRow->nationaliteit = $userObj->nationaliteit;
			$userRow->gemeente = $userObj->gemeente;
			$userRow->straat = $userObj->straat;
			$userRow->huisnummer = $userObj->huisnummer;
			$userRow->huisletter= $userObj->huisletter;
			$userRow->huisnummer_toevoeging = $userObj->huisnummer_toevoeging;
			$userRow->buitenland_adres_1 = $userObj->buitenland_adres_1;
			$userRow->buitenland_adres_2 = $userObj->buitenland_adres_2;
			$userRow->buitenland_adres_3 = $userObj->buitenland_adres_3;
			$userRow->save();
		}
		else
		{
			/*
			 * check if everything is the same, if not add an event
			 */
			
			$overheidusrObj = $overheidTable->find($user->bsn)->current();
			
			$userArr = $userObj->toArray();
			$govUserArr = $overheidusrObj->toArray();
			
			$diff = array_diff($govUserArr, $userArr);
			if(!empty($diff))
			{
				foreach ($diff as $k=>$v)
				{
					$newEvent = $eventTable->createRow();
					$newEvent->bsn = $user->bsn;
					$newEvent->short_desc = ucfirst($k)." is veranderd.";
					$newEvent->desc = ucfirst($k)." is veranderd naar ".$v.".";
					$newEvent->date = date('Y-m-d H:i:s');
					$newEvent->timeline = 1;
					$newEvent->save();
					
					$userObj->$k = $v;
					$userObj->save();
				}
			}
			
			
		}
		
		$userData = $userObj->toArray();
		
		
		$letterObj = $letterTable->fetchAll($letterTable->select()->where("bsn = ?", $user->bsn));
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
		
		$passportObj = $passportTable->fetchAll($passportTable->select()->where("bsn = ?", $user->bsn));
		if($passportObj != null)
		{
			foreach ($passportObj as $passport)
			{
				$dataRow = array();
				$dataRow['documentnr'] = $passport->documentnr;
				$dataRow['vervaldatum'] = $passport->vervaldatum;
				
				$kvkjson = Zend_Json::decode(file_get_contents("http://officieel.openkvk.nl/json/".$passport->kvknummer));
				$dataRow['instantie'] = array(
					"naam"=> $kvkjson[0]['rechtspersoon'],
					"adres"=> $kvkjson[0]['adres'],
					"woonplaats"=>$kvkjson[0]['woonplaats'],
					"postcode"=>$kvkjson[0]['postcode'],
					"kvknummer"=>$kvkjson[0]['kvks'],
				);
		
				$dataRow['ontvangen'] = $passport->ontvangen;
				$dataRow['beschrijving'] = $passport->beschrijving;
				array_push($passportData, $dataRow);
			}
		}
		
		$kvkonuserTable = new Application_Model_DbTable_Kvkonuser();
		
		$kvkName = $kvkTable->info('name');
		$kvkuserName = $kvkonuserTable->info('name');
		
		
		$kvkObj = $kvkTable->fetchAll(
				$kvkTable->select()->from(array("ok" => $kvkTable->info('name')))
		->join(array(
				"ku" => $kvkonuserTable->info("name")
		),"ok.kvks = ku.kvk"
		)->where('ku.bsn = ?',$user->bsn)
				->setIntegrityCheck(false));
		
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
				'message' => array(
					'user'=>$userData,
					'kvkdata'=>$kvkData,
					'letters'=>$letterData,
					'passport_events'=>$passportData,
		) 
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





