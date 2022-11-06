import React, { useState } from 'react';
import logo from './img/mouse.png';
import security from './img/security-1.png';
import demoVideo from './vid/jiggler.mp4';
import { PayPalButton } from "react-paypal-button-v2";
import { SocialIcon } from 'react-social-icons';
import './App.css';
import './fonts.css';

function App() {
  const windowsDownloadLink = "https://dl.dropboxusercontent.com/s/qne9ijxtdqimsf5/jiggler-app-windows.zip?dl=0";
  const macOSDownloadLink = "https://dl.dropboxusercontent.com/s/63id263vqa5bec5/jiggler-app-macos.zip?dl=0";
  
  const [donateAmount,setDonateAmount] = useState(1);
  const [triggerDownload,setTriggerDownload] = useState(false);

  function createPaypalButton() {
    return (
    <PayPalButton
        amount={donateAmount}
        onSuccess={(details, data) => {
          alert("Transaction completed by " + details.payer.name.given_name);
        }}
        options={{
          clientId: "AQbzM5mlOHMXET0-9a1sMY1NFNcYkbEvXLvNcEh9HV7krcoxhjKMsTwJof444NTG0v2RvHCX-rPLwpYw"
        }}
    />)
  }

  
  return (
    <div id="parentContainer">
        
        <div className='text-center mt-5'>
          <img src={logo} className="App-logo" alt="logo" />
          <p className="App-title">Jiggler</p>
          <button className='download-btn btn btn-md' onClick={() => setTriggerDownload(true)}>
            <a href={windowsDownloadLink} download="jiggler">Download (Windows)</a>
          </button>
          <span className='mx-2'></span>
          <button className='download-btn btn btn-md' onClick={() => setTriggerDownload(true)}>
            <a href={macOSDownloadLink} download="jiggler">Download (macOS)</a>
          </button>
          {triggerDownload ?
          <div className='bg-dark p-2 mt-3 border-secondary border text-center'>
            <p className='mt-1'><strong>Windows: </strong>Extract the contents of the zip to a folder of your liking and run 'Jiggler.exe'</p>
            <p className='mt-1'><strong>macOS: </strong>Extract the contents of the zip to a folder of your liking and run 'Jiggler.bat'</p>
          </div>
          : null}
        </div>

        <div className='row m-5 mt-4'>
          <div className='col-4'>
            <div className='jumbotron'>
              <h5>Security</h5>
              <hr/>
              <p className='text-left'>Jiggler is safe to use. It does not track any user data. All it does is move your mouse. The link below reflects the scan done by VirusTotal.</p>
              <span>{'>> '}</span><a style={{fontSize: '17px'}} className='text-light' target="_blank" href="https://www.virustotal.com/gui/file/611008834437e14acab4671f6b616f30e10140629d2cb3a2084c8871133c6344/detection">
                Click to view virus total report
              </a>
              <img className="mt-3" width="100%" src={security} alt="Security Scan"/>
            </div>
          </div>
          <div className='col-4'>
            <div className='jumbotron'>
              <h5>Support me!</h5>
              <hr/>
              <p>If you would like to, feel free to donate any amount to the PayPal account below! Every bit of contribution helps!</p>
              <div className='text-center mb-3'>
                <label className='mx-3'>Donation amount</label>
                <input 
                type="number" 
                id="typeNumber"
                value={donateAmount < 0 ? 1 : donateAmount}
                min="1"
                onChange={(e) => setDonateAmount(e.target.value)} 
                required />
                <label className='mx-3'>USD</label>
              </div>
              {createPaypalButton()}
            </div>
          </div>
          <div className='col-4'>
            <div className='jumbotron'>
              <h5>Purpose</h5>
              <hr/>
              <p className='text-left'>Jiggler aims to replace your traditional physical mouse jigglers with a software. The software will aim to mimic human like mouse movements to evade Monitoring Software and save you some dosh!</p>
              <small>Jiggler application demonstration (Windows)</small>
              <video width="100%" controls className='mt-1'>
                <source src={demoVideo} type="video/mp4"/>
              Your browser does not support the video tag.
              </video>
            </div>
          </div>
        </div>

        <footer className='text-center bg-dark w-100 p-5'>
          <p>Copyright © 2022 Bytecode™</p>
          <p>Owned & Built by Alwinderjit</p>
          <SocialIcon url="https://www.linkedin.com/in/alwinder/" />
        </footer>

    </div>
  );
}

export default App;
