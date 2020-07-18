DEV = "false"

if (process.env.NODE_ENV === "false") {
    DEV = "false"
}

OPTIONS = {
    CREDITENTIALS: {},
    S_ID: "",
    S_NAME: "",
    MOD_ID: ""
}

if (DEV === "true") {
    let config_file = require("./config.json");
    OPTIONS.CREDITENTIALS = config_file.CREDITENTIALS;
    OPTIONS.S_ID = config_file.SPREADSHEET_ID;
    OPTIONS.S_NAME = config_file.SPREADSHEET_NAME;
    OPTIONS.MOD_ID = config_file.MODID;
} else {
    OPTIONS.CREDITENTIALS = JSON.parse(process.env.CREDITENTIALS);
    OPTIONS.S_ID = process.env.SPREADSHEET_ID;
    OPTIONS.S_NAME = process.env.SPREADSHEET_NAME;
    OPTIONS.MOD_ID = process.env.MOD_ID;
}

LANG_PATH = "src/main/resources/assets/mod_id/lang/".replace("mod_id",OPTIONS.MOD_ID)

const {GoogleSpreadsheet} = require('google-spreadsheet');

const doc = new GoogleSpreadsheet(OPTIONS.S_ID);
const fs = require("fs")

async function main() {
    await doc.useServiceAccountAuth(OPTIONS.CREDITENTIALS);
    await doc.loadInfo();
    let sheet = null
    for (let index = 0; index < doc.sheetsByIndex.length; index++) {
        if (doc.sheetsByIndex[index]["_rawProperties"]["title"] === OPTIONS.S_NAME) {
            sheet = doc.sheetsByIndex[index]
            break
        }
    }
    const rows = await sheet.getRows();
    await sheet.loadCells();
    const columnCount = sheet.columnCount;

    const lang_files = {

    }

    for (let index = 1; index < columnCount; index++) {
        lang_files[sheet.getCell(0, index).value] = {}
    }

    for (let index = 0; index < rows.length; index++) {
        const row = rows[index];
        for (let index2 = 1; index2 < columnCount; index2++) {
            var lang_value = sheet.getCell(row.rowIndex - 1, index2).value;
            if (lang_value === null) lang_value = sheet.getCell(row.rowIndex - 1, 1).value;
            lang_files[sheet.getCell(0, index2).value][sheet.getCell(row.rowIndex - 1, 0).value] = lang_value;
        }
    }
    console.log(lang_files)
    for (const key in lang_files) {
        if (lang_files.hasOwnProperty(key) && key !== "null") {
            const content = lang_files[key];
            fs.writeFileSync(LANG_PATH + key + ".json", JSON.stringify(content,null, 4))
        }
    }
}

main();