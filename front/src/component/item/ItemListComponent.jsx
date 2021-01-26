import React, {Component} from 'react';
import ApiService from '../../ApiService';

class ItemlistComponent extends Component{

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
            items: [],
            type:'',
            name:'',
            value:'',
            message: null
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
        ApiService.fetchItems()
        .then( res => {
            this.setState({
                items:res.data
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
            this.setState({
                item: this.state.items.filter(item => item.id !== itemID)
            });
            this.reloadItemList();
        })
        .catch(err => {
            console.log('deleteItemList() Error! ', err);
        })
    }

    editItem = (ID) => {
        window.localStorage.setItem("itemID", ID);
        this.props.history.push('/edit-item');
    }

    addItem = () => {
        window.localStorage.removeItem("itemID");
        this.props.history.push('/add-item');
    }

    onChange = (e) => {
        this.setState({
            [e.target.name] : e.target.value
        })
    }

    onReset = (e) => {
        this.setState({
            type:"", name:"", value:""
        })
    }

    // e.preventDefault() : submit, a 태그를 통한 페이지 이동, input 전송 등의 동작을 막아준다.
    saveItem = (e) =>{
        e.preventDefault();

        let item ={
            type: this.state.type,
            name: this.state.name,
            value: this.state.value,
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

    render(){
        return(
            <div>
                <h2>Item List</h2>
                <button onClick={this.addItem}> Add Item </button>
                <table>
                    <thead>
                        <tr>
                            <th>Type</th>
                            <th>Name</th>
                            <th>Value</th>
                            <th>Edit</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.items.map( item =>
                            <tr key={item.id}>
                                <td>{item.type}</td>
                                <td>{item.name}</td>
                                <td>{item.value}</td>
                                <td>
                                    <button onClick={()=>this.editItem(item.id)}>Edit</button>
                                    <button onClick={()=>this.deleteItem(item.id)}>Delete</button>
                                </td>
                            </tr>
                            )}
                        <tr>
                            <td>
                                <input type="text" placeholder="input item type" name="type" value={this.state.type} onChange={this.onChange} />
                            </td>
                            <td>
                                <input type="text" placeholder="input item name" name="name" value={this.state.name} onChange={this.onChange} />
                            </td>
                            <td>
                                <input type="text" placeholder="input item value" name="value" value={this.state.value} onChange={this.onChange}/>
                            </td>
                            <td>
                                <button onClick={this.saveItem}>Save</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        );
    }

}

export default ItemlistComponent;