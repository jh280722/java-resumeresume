import React from 'react';
import AppRouter from './component/route/RouterComponent';
import styled, { createGlobalStyle } from "styled-components";

const GlobalStyle = createGlobalStyle`
  body {
    margin:0px;
    padding:0px;
  }
`;

function App() {
  return (
    <div>
      <GlobalStyle></GlobalStyle>
      <AppRouter />
    </div>
  );
}

export default App;