<?php

class TestController extends Zend_Controller_Action
{

    public function init()
    {
		$this->_helper->viewRenderer->setNoRender(TRUE);
    }
	
	public function createLetterAction()
	{
		$raw = $this->getRequest()->getRawBody();
		$json = Zend_Json::decode($raw);
		$onderwerp = $json['onderwerp'];
		$adres = $json['adres'];
		$prioriteit = $json['prioriteit'];
		$verzenddatum = $json['verzenddatum'];
		$plaats = $json['plaats'];
		$postcode = $json['postcode'];
		$bsn = $json['bsn'];
		if(!isset($onderwerp) || !isset($adres) || !isset($prioriteit) || !isset($verzenddatum) || !isset($plaats) || !isset($postcode) || !isset($bsn)){
			$this->getResponse()->setHttpResponseCode(400);
			$data = array('status'=>'FAILED','message'=>'NO_DATA_PROVIDED', 'input'=>$json);
			echo $this->_helper->json($data);
			exit();
		}
		
		$letterTable = new Application_Model_DbTable_Brieven ();
		$eventTable =new Application_Model_DbTable_Events();
		$row = $letterTable->createRow();
		$row->onderwerp = $onderwerp;
		$row->adres = $adres;
		$row->prioriteit = $prioriteit;
		$row->verzenddatum = $verzenddatum;
		$row->Plaats = $plaats;
		$row->postcode = $postcode;
		$row->bsn = $bsn;
		$row->save();
		
		$event = $eventTable->createRow();
		$event->bsn = $row->bsn;
		$event->short_desc = 'Er is een brief verstuurd: '.$row->onderwerp;
		$event->desc = '';
		$event->date = date('Y-m-d H:i:s');
		$event->timeline = 0;
		$event->panic_level=1;
		$event->save();
		
		
	}
}