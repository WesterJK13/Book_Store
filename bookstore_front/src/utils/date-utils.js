export const formatDateLocal = (date) => {
    date = date.split('T')[0]
    const reverseDate = date.split('-')
    date = reverseDate[2] + '-' + reverseDate[1] + '-' + reverseDate[0];
    return date
}