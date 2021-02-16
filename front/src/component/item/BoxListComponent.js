import React, {useState, useEffect} from 'react';
import ItemlistComponent from './ItemListComponent';
import ApiService from '../../ApiService';


// 박스 리스트 출력 컴포넌트
function BoxListComponent(){
    const [boxes, setBoxes] = useState([]);
    const [boxName, setBoxName] = useState("");
    const [btnState, setBtnState] = useState(false); // onClick 이벤트 시 렌더링을 시키기 위한 상태 변화 확인용 state

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
        })
        .catch(err => {
            console.log('addBox() 에러', err);
        });
    }

    const deleteBox = (boxID) => {
        ApiService.deleteBox(boxID)
        .then(res => {
            setBtnState(!btnState); // onClick 이벤트 상태 변화
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
                <div key={box.id}>
                    <button onClick={()=>deleteBox(box.id)}>박스 삭제</button>
                    <ItemlistComponent boxID={box.id.toString()}/>
                </div>
            )}
            <input type="text" placeholder="box name" name={"setBoxName"} value={boxName} onChange={onChangeBoxName}/>
            <button onClick={addBox}>박스 추가</button>
        </>
    )
}

export default BoxListComponent;