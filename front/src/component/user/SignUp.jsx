import React, {useState} from 'react'
import './css/SignUp.module.css'
import ApiService from "../../ApiService";

const HTTP_CREATED = 201;

function SignUp(pros) {
    const [user, setUser] = useState({
        name: "",
        email: "",
        password: "",
        passwordConfirm: ""
    });

    const onChange = (e) => {
        setUser({
            ...user,
            [e.target.name]: e.target.value
        });
    }

    const createUser = (e) => {
        e.preventDefault();

        let userData = {
            name: user.name,
            email: user.email,
            password: user.password,
            passwordConfirm: user.passwordConfirm,
        }

        ApiService.createUser(userData)
            .then(res => {
                if (res.status === HTTP_CREATED)
                    pros.history.push(`/`);
                else
                    alert("회원가입 실패");
            })
            .catch(err => {
                alert("회원가입 에러");
                console.log("회원가입 에러!\n", err);
            });
    }

    return (
        <div className="container">
            <h1>회원가입</h1>
            <hr/>

            <label htmlFor="email"><b>이메일</b></label>
            <input type="text" placeholder="이메일을 입력해주세요" name="email" onChange={onChange} required/>

            <label htmlFor="name"><b>이름</b></label>
            <input type="text" placeholder="이름을 입력해주세요" name="name" onChange={onChange} required/>

            <label htmlFor="psw"><b>비밀번호</b></label>
            <input type="text" placeholder="비밀번호를 입력해주세요" name="password" onChange={onChange} required/>

            <label htmlFor="psw-repeat"><b>비밀번호 확인</b></label>
            <input type="password" placeholder="비밀번호를 다시 한번 입력해주세요" name="passwordConfirm" onChange={onChange}
                   required/>

            <div className="clearfix">
                <button onClick={createUser}>회원가입</button>
                <button type="button" className="cancelbtn">취소</button>
            </div>
        </div>
    )
}

export default SignUp;