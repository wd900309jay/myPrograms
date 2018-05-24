const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

var getLevel = function (level) {
  let leveltext = '';
  switch (level) {
    case 1:
      leveltext = '不满意'
      break;
    case 2:
      leveltext = '一般'
      break;
    case 3:
      leveltext = '不错'
      break;
    case 4:
      leveltext = '满意'
      break;
    case 5:
      leveltext = '超赞'
      break;
    default:
      break;
  }
  return leveltext;
}

module.exports = {
  formatTime: formatTime,
  getLevel: getLevel
}
