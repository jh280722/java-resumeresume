import React, {useState, useEffect} from 'react';
import ItemlistComponent from './ItemListComponent';
import ApiService from '../../ApiService';
import styled,{css} from 'styled-components';
import {Close} from '@styled-icons/ionicons-sharp/Close';
import {PlusCircleFill}from '@styled-icons/bootstrap/PlusCircleFill'

const DocContainer = styled.div`
    display : flex;
    flex-direction:column;
    align-items:stretch;
`;

const BoxContainer = styled.div`
    border: 1px solid gray;
    border-radius: 20px;
    margin : 10px;
    padding : 10px;
    display : flex;
    flex-direction:column;
    align-items:stretch;
    #deleteBoxBtn{
        align-self : flex-end;
    }
`;

const AddBoxBtn = styled.div`
    align-self : center;
    display : none;
    flex-direction:column;
    input{
        height : 30px;
        width : 150px;
        font-size : 25px;
    }
    ${props =>
        props.addBoxState &&
        css`
            display:flex;
        `}
`;

const Itemlist = styled.div`
`;

const BoxDel = styled(Close)`
    color: #3B3B3B;
    width : 30px;
    height : 30px;
`;

const BoxPlus = styled(PlusCircleFill)`
    color: #3B3B3B;
    width : 60px;
    height : 60px;
    align-self : center;
    margin : 10px;
`;

// 박스 리스트 출력 컴포넌트
function BoxListComponent(props){
    const [boxes, setBoxes] = useState([]);
    const [docs, setDocs] = useState([]);
    const [boxName, setBoxName] = useState("");
    const [addBoxState, setAddBoxState] = useState(false);
    const [btnState, setBtnState] = useState(false); // onClick 이벤트 시 렌더링을 시키기 위한 상태 변화 확인용 state

    useEffect(() => {
        ApiService.fetchDocumentsByID(props.docID)
            .then(res => {
                setDocs(res.data);
                setBoxes(res.data.boxes);
                if(props.docID != 0){
                    setAddBoxState(true);
                }
            })
            .catch(err => {
                console.log('reloadBoxList() Error! ',err);
            })
    },[btnState,props.docID]);

    const addBox = (e) =>{
        e.preventDefault();
        let targetDoc ={
            id : docs.id,
            name : docs.name,
        }
        let box ={
            name: boxName,
            document : targetDoc,
        }
        ApiService.addBox(box)
            .then(res => {
                setBtnState(!btnState); // onClick 이벤트 상태 변화
                setBoxName("");
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
        <DocContainer>
            <div>
                {boxes.map(box=>
                    <BoxContainer key={box.id}>
                        <BoxDel id="deleteBoxBtn" onClick={()=>deleteBox(box.id)}>박스 삭제</BoxDel>
                        <Itemlist>
                            <ItemlistComponent boxID={box.id.toString()}/>
                        </Itemlist>
                    </BoxContainer>
                )}
            </div>
            <AddBoxBtn addBoxState={addBoxState}>
                <input type="text" placeholder="항목 이름" name={"setBoxName"} value={boxName} onChange={onChangeBoxName}/>
                <BoxPlus onClick={addBox}>박스 추가</BoxPlus>
            </AddBoxBtn>
        </DocContainer>
    )
}

export default BoxListComponent;