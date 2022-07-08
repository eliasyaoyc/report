const puppeteer = require('puppeteer');

var params = process.argv;

var input = params[2];
var output = params[3];

(async () => {
  const browser = await puppeteer.launch({
    headless: true,
    executablePath: '/usr/bin/chromium',
    args: ["--no-sandbox", "--disable-gpu"],
  });
  const page = await browser.newPage();
  await page.goto('file:///' + input);
  await page.pdf({
    path: output,
    printBackground: true,
    width: 1200
  });

  await browser.close();
})();