import React, {useState, useEffect} from 'react';
import BoxlistComponent from './BoxListComponent';
import ApiService from '../../ApiService';
import styled from 'styled-components';
import {Close} from '@styled-icons/ionicons-sharp/Close';
import {PlusCircleFill}from '@styled-icons/bootstrap/PlusCircleFill'

const MainContainer = styled.div`
    width : 100vw;
    display : flex;
    flex-direction:column;
    align-items : center;
`;

const ContentsContainer = styled.div`
    width : 80vw;
    display : flex;
    flex-direction:column;
`;

const DocListContainer = styled.div`
    display : flex;
    flex-direction:row;
    align-items:center;
    input{
        height : 40px;
        width : 100px;
        font-size : 18px;
    }
`;

const DocContainer = styled.div`
    display : flex;
    margin : 10px 5px 0px 5px;
    padding : 0px 0px 0px 20px;
    border-width: 1px 1px 0px 1px;
    border-style: solid;
    border-color: gray;
    border-radius: 10px 10px 0px 0px / 10px 10px 0px 0px;
    color: #232323;
    font-weight : bold;
    font-size : 18px;
`;

const DocDel = styled(Close)`
    color: #3B3B3B;
    width : 25px;
    height : 25px;
`;

const DocPlus = styled(PlusCircleFill)`
    color: #3B3B3B;
    width : 50px;
    height : 50px;
    align-self : center;
    margin : 10px;
`;

// Document 리스트 출력 컴포넌트
function DocumentListComponent(){
    const [documents, setDocuments] = useState([]);
    const [documentId, setDocumentId] = useState("0");
    const [documentName, setDocumentName] = useState("");
    const [btnState, setBtnState] = useState(false); // onClick 이벤트 시 렌더링을 시키기 위한 상태 변화 확인용 state

    useEffect(() => {
        ApiService.fetchDocuments()
        .then(res => {
            setDocuments(res.data);
        })
        .catch(err => {
            console.log('reloadBoxList() Error! ',err);
        })
    },[btnState]);

    const addDoc = (e) =>{
        e.preventDefault();
        let document ={
            name: documentName
        }
        ApiService.addDocument(document)
        .then(res => {
            setBtnState(!btnState); // onClick 이벤트 상태 변화
            setDocumentName("");
        })
        .catch(err => {
            console.log('addDoc() 에러', err);
        });
    }

    const deleteDoc = (docID) => {
        ApiService.deleteDocument(docID)
        .then(res => {
            setBtnState(!btnState); // onClick 이벤트 상태 변화
        })
        .catch(err => {
            console.log('deleteDoc() 에러! ', err);
        })
    }

    const onChangeDocName = (e) =>{
        setDocumentName(e.target.value);
    }

    const changeDoc = (e) =>{
        setDocumentId(e.target.dataset.docid);
        setBtnState(!btnState);
    }

    return(
        <MainContainer>
            <ContentsContainer>
                <DocListContainer>
                    {documents.map(document=>
                        <DocContainer key={document.id}>
                            <p onClick={changeDoc} data-docid={document.id}>{document.name}</p>
                            <DocDel onClick={()=>deleteDoc(document.id)}>Document 삭제</DocDel>
                        </DocContainer>
                    )}
                    <input type="text" placeholder="항목 이름" name={"setDocumentName"} value={documentName} onChange={onChangeDocName}/>
                    <DocPlus onClick={addDoc}>Document 추가</DocPlus>
                </DocListContainer>
                <BoxlistComponent docID={documentId}/>
            </ContentsContainer>
        </MainContainer>
    )
}

export default DocumentListComponent;