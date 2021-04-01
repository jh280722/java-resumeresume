  
import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import DocumentListComponent from "../item/DocumentListComponent";
import SignIn from "../user/SignIn"
import SignUp from "../user/SignUp"


/*
리액트 Router
SPA를 구현하기 위해 사용하는 라우터, 요청에 맞는 컴포넌트만 라우팅한다.
그중 react-router-dom은 웹을 위해 사용된다.
<BrowserRouter> : HTML5의 history API를 활용하여 UI를 업데이트한다.
<HashRouter> : URL의 hash를 활용한 라우터, 정적인 페이지에 적합하다.
<Route> : 요청받은 pathname에 해당하는 컴포넌트를 렌더링한다.
<Switch> : path 충돌이 일어나지 않도록 <Route>들을 관리한다.
           <Switch> 내부에 <Route>들을 넣으면 제일 처음 매칭되는 <Route>만 선별하여 실행하기 때문에
           충돌을 막아줄 수 있다.
*/
function AppRouter(){

    return(
        <div>
            <BrowserRouter>
                <div>
                    <Switch>
                        <Route exact path="/" component={DocumentListComponent} />
                        <Route path="/items" component={DocumentListComponent} />
                        <Route path="/signin" component={SignIn} />
                        <Route path="/signup" component={SignUp} />
                    </Switch>
                </div>
            </BrowserRouter>
        </div>
    )
}

export default AppRouter;