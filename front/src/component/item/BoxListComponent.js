import React, {useState, useEffect} from 'react';
import ItemlistComponent from './ItemListComponent';
import ApiService from '../../ApiService';


// 박스 리스트 출력 컴포넌트
function BoxListComponent(){
    const [boxes, setBoxes] = useState([]);
    const [boxName, setBoxName] = useState("");
    const [message, setMessage] = useState("");
    const [btnState, setBtnState] = useState(false); // onClick 이벤트 시 렌더링을 시키기 위한 상태 변화 확인용 state (코드 리뷰 부탁드립니다.)

    useEffect(() => {
        ApiService.fetchBoxes()
        .then(res => {
            setBoxes(res.data);
        })
        .catch(err => {
            console.log('reloadBoxList() Error! ',err);
        })
    },[btnState]);

    const addBox = (e) =>{
        e.preventDefault();
        let box ={
            name: boxName
        }
        ApiService.addBox(box)
        .then(res => {
            setBtnState(!btnState); // onClick 이벤트 상태 변화
            setMessage("박스가 성공적으로 등록되었습니다.");
            console.log(message);
        })
        .catch(err => {
            console.log('addBox() 에러', err);
        });
    }

    const deleteBox = (boxID) => {
        ApiService.deleteBox(boxID)
        .then(res => {
            setBtnState(!btnState); // onClick 이벤트 상태 변화
            setMessage("박스가 성공적으로 삭제되었습니다.");
            console.log(message);
        })
        .catch(err => {
            console.log('deleteBox() 에러! ', err);
        })
    }

    const onChangeBoxName = (e) =>{
        setBoxName(e.target.value);
    }

    return(
        <>
            {boxes.map(box=>
                <>
                    <button onClick={()=>deleteBox(box.id)}>박스 삭제</button>
                    <ItemlistComponent boxID={box.id.toString()}/>
                </>
            )}
            <input type="text" placeholder="box name" name={"setBoxName"} value={boxName} onChange={onChangeBoxName}/>
            <button onClick={addBox}>박스 추가</button>
        </>
    )
}

export default BoxListComponent;