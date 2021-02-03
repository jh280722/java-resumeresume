import axios from 'axios';

const ITEM_API_BASE_URL = "http://localhost:8080/items";
const BOX_API_BASE_URL = "http://localhost:8080/boxes"

class ApiService{
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
        return axios.put(ITEM_API_BASE_URL + '/' + item.id, item)
    }

}

export default new ApiService();