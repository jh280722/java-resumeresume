import React, {Component} from 'react';
import ApiService from '../../ApiService';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';

class TempComponent extends Component{

    /*
    컴포넌트 생성기 초기화하는 부분, 마운트되기 전에 호출된다.
    항상 super(props)를 호출해야한다.
    보통 두가지 목적을 위해 사용된다.
    1. this.state에 객체를 할당하여 지역 state를 초기화
    2. 인스턴스에 이벤트 처리 메서드를 바인딩
    */
    constructor(props){
        super(props);

        this.state = {
            box: [],
            items: [],
            boxName : '',
            type:'',
            name:'',
            value:'',
            seq:'',
            message: null,
        }
    }

    /*
    컴포넌트가 마운트된 직후 트리에 삽입된 후에 호출된다.
    */
    componentDidMount(){
        this.reloadItemList();
    }


    /*
    Promise란?
    비동기 작업이 맞이할 미래의 완료 또는 실패와 그 결과 값
    여기서 then과 catch가 쓰였는데, 각각 이런 의미다
    then : Promise가 종료되면 resolve에 들어간 값을 받을 수 있다. 즉 이행상태가 완료 된 후 실행된다.
    catch : reject가 된 경우, 즉 거부상태일때 에러를 잡아준다. 거부상태일때 실행된다고 보면 된다.
    */

    reloadItemList = () => {
        ApiService.fetchBoxesByID(this.props.boxID)
        .then(res => {
            this.setState({
                box: res.data,
                items: res.data.items,
            })
        })
        .catch(err => {
            console.log('reloadItemList() Error! ', err);
        })
    }

    deleteItem = (itemID) => {
        ApiService.deleteItem(itemID)
        .then(res => {
            this.setState({
                message: 'Item Deleted Successfully.'
            });
            this.reloadItemList();
        })
        .catch(err => {
            console.log('deleteItemList() Error! ', err);
        })
    }

    // deleteBox = (boxID) => {
    //     ApiService.deleteBox(boxID)
    //     .then(res => {
    //         this.setState({
    //             message: 'Box Deleted Successfully.'
    //         });
    //         this.reloadItemList();
    //     })
    //     .catch(err => {
    //         console.log('deleteBoxList() Error! ', err);
    //     })
    // }

    editItem = (ID) => {
        window.localStorage.setItem("itemID", ID);
        this.props.history.push('/edit-item');
    }

    onChange = (e) => {
        this.setState({
            [e.target.name] : e.target.value
        })
    }

    onReset = () => {
        this.setState({
            type:"",
            name:"",
            value:"",
            seq:"",
        })
    }

    // e.preventDefault() : submit, a 태그를 통한 페이지 이동, input 전송 등의 동작을 막아준다.
    saveItem = (e) =>{
        e.preventDefault();

        let targetBox ={
            id: e.target.dataset.boxid,
            name : e.target.dataset.boxname,
        }
        let item ={
            type: this.state.type,
            name: this.state.name,
            value: this.state.value,
            seq: this.state.seq,
            box: targetBox,
        }

        ApiService.addItem(item)
        .then(res => {
            this.setState({
                message: item.name + '이 성공적으로 등록되었습니다.'
            })
            console.log(this.state.message);
            this.onReset();
            this.reloadItemList();
        })
        .catch(err => {
            console.log('saveItem() 에러', err);
        });
    }

    addBox = (e) =>{
        e.preventDefault();
        let box ={
            name: this.state.boxName
        }
        ApiService.addBox(box)
        .then(res => {
            this.setState({
                message: box.name + '이 성공적으로 등록되었습니다.'
            })
            console.log(this.state.message);
            this.onReset();
            this.reloadItemList();
        })
        .catch(err => {
            console.log('addBox() 에러', err);
        });
    }

    render(){
        return(
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <Paper>
                        <div key={this.state.box.id}>
                            <h2>{this.state.box.name}</h2>
                            <table>
                                <thead>
                                    <tr>
                                        <th>Type</th>
                                        <th>Name</th>
                                        <th>Value</th>
                                        <th>Seq</th>
                                        <th>Edit</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {this.state.items.map(item =>
                                        <tr key={item.key}>
                                            <td>{item.type}</td>
                                            <td>{item.name}</td>
                                            <td>{item.value}</td>
                                            <td>{item.seq}</td>
                                            <td>
                                                <button onClick={()=>this.editItem(item.id)}>Edit</button>
                                                <button onClick={()=>this.deleteItem(item.id)}>Delete</button>
                                            </td>
                                        </tr>
                                        )}
                                    <tr>
                                        <td>
                                            <input type="text" placeholder="input item type" name={"type"} value={this.state.type} onChange={this.onChange} />
                                        </td>
                                        <td>
                                            <input type="text" placeholder="input item name" name={"name"} value={this.state.name} onChange={this.onChange} />
                                        </td>
                                        <td>
                                            <input type="text" placeholder="input item value" name={"value"} value={this.state.value} onChange={this.onChange}/>
                                        </td>
                                        <td>
                                            <input type="text" placeholder="input seq" name={"seq"} value={this.state.seq} onChange={this.onChange}/>
                                        </td>
                                        <td>
                                            <button onClick={this.saveItem} data-boxid={this.state.box.id} data-boxname={this.state.box.name}>Save</button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </Paper>
                </Grid>
            </Grid>
        );
    }

}

export default TempComponent;