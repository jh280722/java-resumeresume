import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import ApiService from '../../ApiService';
import {Close} from '@styled-icons/ionicons-sharp/Close';

const Box = styled.div`
    #boxName{
        color: #232323;
        font-weight : bolder;
        font-size : 23px;
    }
    #boxContents{
        border-top : 1px solid gray;
        border-bottom : 1px solid gray;
        margin-bottom : 10px;
        display : flex;
        justify-content : space-between;
        #textItem{
            display : flex;
            align-items : center;
            #textItemName{
                border-right : 1px solid gray;
                padding-right : 10px;
                padding-left : 10px;
                color: #3B3B3B;
                font-weight : bold;
                font-size : 18px;
            }
            #textItemValue{
                padding-right : 10px;
                padding-left : 10px;
                color: #3B3B3B;
                font-weight : normal;
                font-size : 15px;
            }
        }
    }
`;

const ItemDel = styled(Close)`
    color: #3B3B3B;
    width : 25px;
    height : 25px;
`;

const AddItem = styled.div`
    display:flex;
    flex-direction:column;
    align-items:center;
    justify-content : space-between;
    select{
        height : 40px;
        width : 100px;
        font-size : 15px;
        margin : 5px;
    }
`;

const TextDiv = styled.div`
    border-top : 1px solid gray;
    border-bottom : 1px solid gray;
    padding-top : 10px;
    padding-bottom : 10px;
    display : flex;
    justify-content : space-between;
    width : 100%;
    #name{
        height :30px;
        margin-right : 5px;
        flex: 1;
    }

    #value{
        height :30px;
        flex: 10;
    }

    button{
        flex: 1;
    }
`;

function ItemlistComponent(props) {
    const [state, setState] = useState({
        box: [],
        items: [],
        boxName: '',
        type: '',
        name: '',
        value: '',
        seq: '',
    });
    const [btnState, setBtnState] = useState(false);
    const [grab, setGrab] = useState(null);
    const [imageState, setImageState] = useState(null);

    useEffect(() => {
        ApiService.fetchBoxesByID(props.boxID)
            .then(res => {
                setState({
                    ...state,
                    box: res.data,
                    items: res.data.items,
                });
            })
            .catch(err => {
                console.log("reloadError!", err);
            })
    }, [btnState]);

    const deleteItem = (itemID) => {
        ApiService.deleteItem(itemID)
            .then(res => {
                setBtnState(!btnState); // onClick 이벤트 상태 변화
            })
            .catch(err => {
                console.log('deleteItem() 에러! ', err);
            })
    }

    const onChange = (e) => {
        setState({
            ...state,
            [e.target.name]: e.target.value
        });
    }

    const onReset = () => {
        setState({
            ...state,
            type: '',
            name: '',
            value: '',
        })
    }

    const saveItem = (e) => {
        e.preventDefault();

        let targetBox = {
            id: e.target.dataset.boxid,
            name: e.target.dataset.boxname,
        }
        let item = {
            type: state.type,
            name: state.name,
            value: state.value,
            box: targetBox,
        }

        ApiService.addItem(item)
            .then(res => {
                onReset();
                setBtnState(!btnState);
            })
            .catch(err => {
                console.log('saveItem 에러', err);
            });
    }

    const _onDragOver = e => {
        e.preventDefault();
    }

    const _onDragStart = e => {
        setGrab(e.target);
        e.dataTransfer.effectAllowed = "move";
        e.dataTransfer.setData("text/html", e.target);
    }

    const _onDragEnd = e => {
        e.dataTransfer.dropEffect = "move";
        setBtnState(!btnState);
    }

    const _onDrop = e => {
        try {
            let params = new URLSearchParams();
            let grabPosition = Number(grab.dataset.id);
            let targetPosition = e.currentTarget.dataset.position;

            params.append('itemId', grabPosition);
            params.append('seq', targetPosition);

            ApiService.dragItem(state.box, params);
        } catch (e) {
            console.log(e);
        }
    }

    return (
        <Box key={state.box.id}>
            <div id="boxName">{state.box.name}</div>
            <div>
                {state.items.map(item =>
                    <div
                        draggable
                        onDragOver={_onDragOver}
                        onDragStart={_onDragStart}
                        onDragEnd={_onDragEnd}
                        onDrop={_onDrop}
                        key={item.id}
                        data-id={item.id}
                        data-position={item.seq}
                        id="boxContents"
                    >
                        {{
                            text: (
                                <div id="textItem">
                                    <p id="textItemName">{item.name}</p>
                                    <p id="textItemValue">{item.value}</p>
                                </div>),
                            textArea: (<p>{item.name} : {item.value}</p>),
                            date: (<p>{item.name} : {item.value}</p>),
                            image: (<p>{item.name} : {item.value}</p>),
                            period: (<p>{item.name} : {item.value}</p>),
                        }[item.type]}
                        <ItemDel onClick={() => deleteItem(item.id)}>Delete</ItemDel>
                    </div>
                )}
                <AddItem>
                    <select
                        id="typeSelect"
                        name={"type"}
                        value={state.type}
                        displayEmpty
                        onChange={onChange}
                        inputProps={{ 'aria-label': 'Without label' }}
                    >
                        <option value="">타입선택</option>
                        <option value={"text"}>짧은 글</option>
                        <option value={"textArea"}>긴 글</option>
                        <option value={"date"}>날짜</option>
                        <option value={"image"}>이미지</option>
                        <option value={"period"}>기간</option>
                    </select>
                    {{
                        text: (<TextDiv>
                            <input type="text" id="name" name={"name"} value={state.name} onChange={onChange} />
                            <input type="text" id="value" name={"value"} value={state.value} onChange={onChange} />
                            <button onClick={saveItem} data-boxid={state.box.id} data-boxname={state.box.name}>Save</button>
                        </TextDiv>),
                        textArea: (<>
                            <input type="text" name={"name"} value={state.name} onChange={onChange} />
                            <input type="text" name={"value"} value={state.value} onChange={onChange} />
                            <button onClick={saveItem} data-boxid={state.box.id} data-boxname={state.box.name}>Save</button>
                        </>),
                        date: (<>
                            <input type="text" name={"name"} value={state.name} onChange={onChange} />
                            <input type="text" name={"value"} value={state.value} onChange={onChange} />
                            <button onClick={saveItem} data-boxid={state.box.id} data-boxname={state.box.name}>Save</button>
                        </>),
                        image: (<>
                            <input type="text" name={"name"} value={state.name} onChange={onChange} />
                            <input type="file" accept="image/jpeg, image/jpg" onChange={imageState} />
                            <button onClick={saveItem} data-boxid={state.box.id} data-boxname={state.box.name}>Save</button>
                        </>),
                        period: (<>
                            <input type="text" name={"name"} value={state.name} onChange={onChange} />
                            <input type="text" name={"value"} value={state.value} onChange={onChange} />
                            <button onClick={saveItem} data-boxid={state.box.id} data-boxname={state.box.name}>Save</button>
                        </>),
                    }[state.type]}
                </AddItem>
            </div>
        </Box>
    )
}
export default ItemlistComponent;