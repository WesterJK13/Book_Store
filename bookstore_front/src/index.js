import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import {BrowserRouter} from "react-router-dom";
import {Context} from "./utils/Context";
import {ContextProducts} from "./utils/ContextProducts";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
      <Context>
          <ContextProducts>
              <App />
          </ContextProducts>
      </Context>
  </BrowserRouter>
);
