import axios from 'axios';
import apiPath from '../../../common/apiPath';
import { alertError } from '../../../common/alertError';

const userInfo = async (phone) => {
  try {
    const { data } = await axios({
      method: 'get',
      url: apiPath.user.get(phone),
    });
    console.log(data);
  } catch (e) {
    alertError();
  }
}

export default userInfo;