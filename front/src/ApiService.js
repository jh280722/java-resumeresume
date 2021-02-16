import axios from 'axios';

const ITEM_API_BASE_URL = "http://localhost:8080/items";
const BOX_API_BASE_URL = "http://localhost:8080/boxes"

class ApiService{
    // item 관리
    fetchItems(){
        return axios.get(ITEM_API_BASE_URL);
    }

    fetchItemByID(itemID){
        return axios.get(ITEM_API_BASE_URL + '/' + itemID);
    }

    deleteItem(itemID){
        return axios.delete(ITEM_API_BASE_URL + '/' + itemID);
    }

    addItem(item){
        return axios.post(ITEM_API_BASE_URL,item);
    }

    editItem(item){
        return axios.put(ITEM_API_BASE_URL + '/' + item.id, item);
    }

    // box 관리
    fetchBoxes(){
        return axios.get(BOX_API_BASE_URL);
    }

    fetchBoxesByID(boxID){
        return axios.get(BOX_API_BASE_URL + '/' + boxID);
    }

    deleteBox(boxID){
        return axios.delete(BOX_API_BASE_URL + '/' + boxID);
    }

    addBox(box){
        return axios.post(BOX_API_BASE_URL,box);
    }

    editBox(box){
        return axios.put(BOX_API_BASE_URL + '/' + box.id, box);
    }
}

export default new ApiService();