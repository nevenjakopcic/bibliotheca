import { extend } from 'vee-validate';
import * as rules from 'vee-validate/dist/rules';
import { messages } from 'vee-validate/dist/locale/en.json';
import isAfter from 'date-fns/isAfter';
import isBefore from 'date-fns/isBefore';
import parse from 'date-fns/parse';

Object.keys(rules).forEach(rule => {
  extend(rule, {
    ...rules[rule],
    message: messages[rule]
  });
});

extend("dateMustBeBefore", {
  params: ["target"],
  message: "Datum {_field_} mora biti prije datuma {target}!",
  validate: (value, { target }) => {
    if (target == null) {
      return true;
    }

    const valueDate = parse(value, "dd.M.yyyy. HH:mm", new Date());
    const targetDate = parse(target, "dd.M.yyyy. HH:mm", new Date());

    return isBefore(valueDate, targetDate);
  }
});

extend("dateMustBeAfter", {
  params: ["target"],
  message: "Datum {_field_} mora biti poslije datuma {target}!",
  validate: (value, { target }) => {
    if (target == null) {
      return true;
    }

    const valueDate = parse(value, "dd.M.yyyy. HH:mm", new Date());
    const targetDate = parse(target, "dd.M.yyyy. HH:mm", new Date());

    return isAfter(valueDate, targetDate);
  }
});