export const multiplierRules = [
  value => {
    if (value)
      return true

    return 'Salary multiplier is required.'
  },
  value => {
    if (Number(value))
      return true;

    return 'Salary multiplier should be a number.'
  },
  value => {
    var num = Number(value)
    if (num > 0)
      return true;

    return 'Salary multiplier should be > 0.'
  },
]

export function stringRules(fieldName, length) {
  return [
    value => {
      if (value)
        return true;

      return `${fieldName} is required.`
    },
    value => {
      if (value.length <= length)
        return true

      return `${fieldName} should be less than ${length} characters.`
    }
  ]
}

export function selectionRules(fieldName) {
  return [
    value => {
      if (value)
        return true

      return `${fieldName} is required.`
    }
  ]
}
