import React, {useState} from 'react';
import ApiService from "../../ApiService";

const HTTP_OK = 200;
const HTTP_UNAUTHORIZED = 401;

function SignIn(pros) {
    const [userToken, setUserToken] = useState({
        email: "",
        password: "",
    });

    const onChange = (e) => {
        setUserToken({
            ...userToken,
            [e.target.name]: e.target.value
        });
    }

    const login = (e) => {
        e.preventDefault();

        console.log('login');

        let userTokenData = {
            email: userToken.email,
            password: userToken.password,
        }

        ApiService.login(userTokenData)
            .then( res => {
                if (res.status === HTTP_OK)
                    pros.history.push(`/`);
                else
                    alert("로그인 실패");
            })
            .catch(err => {
                alert("로그인 에러");
                console.log("로그인 에러!\n", err);
            });
    }

    return (
        <div>
            <h1>로그인</h1>
            <hr/>
            <label>email : </label>
            <input type="text" name={"email"} value={userToken.email} onChange={onChange} required/><br/>
            <label>password : </label>
            <input type="password" name={"password"} value={userToken.password} onChange={onChange} required/><br/>

            <div>
                <button onClick={login}>로그인</button>
            </div>
        </div>
    )
}

export default SignIn;