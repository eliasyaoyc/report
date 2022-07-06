const puppeteer = require('puppeteer');

var input = system.args[1];
var output = system.args[2];

(async () => {
  const browser = await puppeteer.launch({
      headless: true,
      executablePath: '/usr/bin/chromium',
      args: ["--no-sandbox","--disable-gpu"],
  });
  const page = await browser.newPage();
  await page.goto(input);
  await page.screenshot({ path: output, fullPage: true});

  await browser.close();
})();
